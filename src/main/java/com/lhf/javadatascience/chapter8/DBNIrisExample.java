package com.lhf.javadatascience.chapter8;

import org.deeplearning4j.datasets.iterator.DataSetIterator;
import org.deeplearning4j.datasets.iterator.impl.IrisDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.RBM;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.params.DefaultParamInitializer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.api.IterationListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.Arrays;
import java.util.Random;
/**
 * @ClassName DBNIrisExample
 * @Desc 使用DL4j创建深度信念神经网络
 * @Author diandian
 * @Date 2022/1/26 18:10
 **/
public class DBNIrisExample {

    public static void main(String[] args) throws Exception {
        // 为Nd4j设置两个参数
        Nd4j.MAX_SLICES_TO_PRINT = -1;  //要打印的最大片数，这里为-1
        Nd4j.MAX_ELEMENTS_PER_SLICE = -1;  //每片的最大元素个数，这里为-1

        //设置其他参数
        final int numRows = 4;  //行数
        final int numColumns = 1;  //列数
        int outputNum = 3;
        int numSamples = 150;   //总数据数
        int batchSize = 150;  //每批的数据数
        int iterations = 5;
        int splitTrainNum = (int) (batchSize * .8);  //用来分配训练与测试数据，这里我们把数据集的80%用作训练数据，其余数据用作测试数据
        int seed = 123;
        int listenerFreq = 1;  //查看注册到进程的损失函数值的频率，这里设置为1，表示在每个epoch后注册值

        //使用指定的批大小与样本数信息自动加载Iris数据集
        DataSetIterator iter = new IrisDataSetIterator(batchSize, numSamples);
        //对数据进行格式化
        DataSet next = iter.next();
        next.normalizeZeroMeanZeroUnitVariance();

        //把数据划分为训练数据与测试数据，进行划分时，使用随机种子，并且把ENFORCE_NUMERICAL_STABILITY设置为真
        System.out.println("Split data....");
        SplitTestAndTrain testAndTrain = next.splitTestAndTrain(splitTrainNum, new Random(seed));
        DataSet train = testAndTrain.getTrain();
        DataSet test = testAndTrain.getTest();
        Nd4j.ENFORCE_NUMERICAL_STABILITY = true;

        //创建模型
        System.out.println("Build model....");
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed) // 锁定权重初始化以进行调整
                .iterations(iterations) // 训练迭代预测/分类和反向传播
                .learningRate(1e-6f) // 定义优化步长，选择
                .optimizationAlgo(OptimizationAlgorithm.CONJUGATE_GRADIENT) // 选择反向传播算法计算梯度
                .l1(1e-1).regularization(true).l2(2e-4)
                .useDropConnect(true)
                .list(2) // # 在list()方法中，设置参数为2，指定神经网络层数（包含输入层）
                .layer(0, new RBM.Builder(RBM.HiddenUnit.RECTIFIED, RBM.VisibleUnit.GAUSSIAN)  //创建神经网络的第一个层，这里的0为层索引， RBM.VisibleUnit.GAUSSIAN 让模型可以处理连续值
                        .nIn(numRows * numColumns) // 输入节点
                        .nOut(3) // 全连接的隐藏层节点。 如果有多个图层，则添加列表。
                        .weightInit(WeightInit.XAVIER) // 权重初始化
                        .k(1) // 对比散度
                        .activation("relu") // 激活函数类型
                        .lossFunction(LossFunctions.LossFunction.RMSE_XENT) // 损失函数类型
                        .updater(Updater.ADAGRAD)  //优化学习率
                        .dropOut(0.5)
                        .build()
                ) // 为神经网络创建层1
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.MCXENT)
                        .nIn(3) // 输入节点
                        .nOut(outputNum) // 输出节点
                        .activation("softmax")
                        .build()
                ) // NN layer type
                .build();
        //创建模型并进行初始化
        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        //配置好模型之后，进行训练
        //model.setListeners(Arrays.asList(new ScoreIterationListener(listenerFreq),
                //new GradientPlotterIterationListener(listenerFreq),
                //new LossPlotterIterationListener(listenerFreq)));

        //配置好模型之后，进行训练
        model.setListeners(Arrays.asList((IterationListener) new ScoreIterationListener(listenerFreq)));
        System.out.println("Train model....");
        model.fit(train);

        //使用如下代码对权重进行评估
        System.out.println("Evaluate weights....");
        for(org.deeplearning4j.nn.api.Layer layer : model.getLayers()) {
            INDArray w = layer.getParam(DefaultParamInitializer.WEIGHT_KEY);
            System.out.println("Weights: " + w);
        }

        //评估模型
        System.out.println("Evaluate model....");
        Evaluation eval = new Evaluation(outputNum);
        INDArray output = model.output(test.getFeatureMatrix());

        for (int i = 0; i < output.rows(); i++) {
            String actual = test.getLabels().getRow(i).toString().trim();
            String predicted = output.getRow(i).toString().trim();
            System.out.println("actual " + actual + " vs predicted " + predicted);
        }

        eval.eval(test.getLabels(), output);
        System.out.println(eval.stats());
        System.out.println("****************Example finished********************");


       /* OutputStream fos = Files.newOutputStream(Paths.get("coefficients.bin"));
        DataOutputStream dos = new DataOutputStream(fos);
        Nd4j.write(model.params(), dos);
        dos.flush();
        dos.close();
        FileUtils.writeStringToFile(new File("conf.json"), model.getLayerWiseConfigurations().toJson());

        MultiLayerConfiguration confFromJson = MultiLayerConfiguration.fromJson(FileUtils.readFileToString(new File("conf.json")));
        DataInputStream dis = new DataInputStream(new FileInputStream("coefficients.bin"));
        INDArray newParams = Nd4j.read(dis);
        dis.close();
        MultiLayerNetwork savedNetwork = new MultiLayerNetwork(confFromJson);
        savedNetwork.init();
        savedNetwork.setParams(newParams);
        System.out.println("Original network params " + model.params());
        System.out.println(savedNetwork.params());


*/
    }
}