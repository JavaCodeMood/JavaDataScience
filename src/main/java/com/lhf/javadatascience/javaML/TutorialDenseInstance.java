package com.lhf.javadatascience.javaML;

import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 * @ClassName TutorialDenseInstance
 * @Desc
 * @Author diandian
 * @Date 2022/1/13 14:57
 **/
/**
 * 本教程展示了使用 Java-ML 的第一步。它会告诉你
 * 如何创建一个以后可以使用的 {@link net.sf.javaml.core.Instance}
 * 在 {@link net.sf.javaml.core.Dataset} 和机器学习中
 *算法。
 *
 * 在这个类中，我们只使用 {@link net.sf.javaml.core.DenseInstance}。
 * 这种类型的实例对每个属性都有一个值，并且有一个可选的
 * 类标签。
 * */
public class TutorialDenseInstance {

    public static void main(String[] args) {
        /* values of the attributes. */
        double[] values = new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

        /*
         * DenseInstance 构造函数的最简单化身只会
         * 将双数组作为参数，将创建一个给定的实例
         * 值作为属性，没有设置类值。对于无人监督的机器
         * 学习技巧 这可能是最方便的构造函数了。
         */
        //创建一个具有十个属性的实例。这些属性的值是 1 到 10。此实例的类标签未设置。
        Instance instance = new DenseInstance(values);

        System.out.println("Instance with only values set: ");
        System.out.println(instance);
        System.out.println();
        /*
         * 要创建具有类值集的实例，可以使用这两个
         * 参数构造函数，它将值和类值作为
         * 参数。
         */
        //要创建具有 10 个属性、值 1 到 10 且类标签为“正”的实例，我们可以使用以下代码。请注意，类标签可以是任何对象。
        Instance instanceWithClassValue = new DenseInstance(values, 1);

        System.out.println("Instance with class value set to 1: ");
        System.out.println(instanceWithClassValue);
        System.out.println();

    }

}