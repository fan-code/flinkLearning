package myflink.DemoTest;

public class A <T extends Number>{

    private T a;

    public A() {}

    public A(T a) {
        this.a = a;
    }

    // 泛型构造器
    public <E> A(E e) {
        System.out.println("调用泛型构造器：" + e);
    }

    public T getA() {
        return a;
    }

    public void setA(T a) {
        this.a = a;
    }

    public static void main(String[] args) {
        A<Integer> a1 = new A<Integer>();
        a1.setA(1);
        System.out.println(a1.getA());


        // 构造器传入int类型的参数，则调用构造器public A(T a)
        A<Integer> a2 = new A<Integer>(2);
        System.out.println(a2.getA());

        // 构造器传入字符串类型的参数，则调用泛型构造器
        A<Integer> a3 = new A<Integer>("字符串1");
        System.out.println(a3.getA());

        // 指定了泛型构造器类型形参E为String类型
        A<Integer> a4 = new <String> A<Integer>("字符串1");
        System.out.println(a3.getA());

        // 使用菱形语法：左边指定了类型形参T为Integer，可以通过泛型推断，得知右边<>里面应该是什么类型
        A<Integer> a5 = new A<>("字符串2");


        // 下面错误。如果显示指定了泛型构造器的类型形参的实际类型，则不可以使用菱形语法
        //A<Integer> a6 = new <String> A<>("字符串2");// 使用菱形语法
    }


}
