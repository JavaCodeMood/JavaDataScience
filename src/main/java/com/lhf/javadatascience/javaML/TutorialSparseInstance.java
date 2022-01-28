package com.lhf.javadatascience.javaML;

import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;

/**
 * @ClassName TutorialSparseInstance
 * @Desc 要创建 SparseInstance，您只需指定 Instance 具有的属性数量，然后设置特定的属性值
 *  展示如何创建一个 SparseInstance。SparseInstance的所有属性的默认值为* 0，但您可以将其中一些属性设置为其他值。只是
 *   与 DenseInstance 一样，SparseInstance 也有一个可选的类标签。
 * @Author diandian
 * @Date 2022/1/13 15:08
 **/
public class TutorialSparseInstance {

    public static void main(String[]args){
        /*
         * 这里我们将创建一个具有 10 个属性的实例，但只会
         为索引为 1,3 和 7 的属性设置一个值。
         */
        //创建具有 10 个属性的实例
        Instance instance = new SparseInstance(10);
        // 设置特定属性的值
        instance.put(1, 1.0);
        instance.put(3, 2.0);
        instance.put(7, 4.0);
        System.out.println(instance);
    }

}