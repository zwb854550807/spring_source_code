public class MainTest {


    public static class base{

        public base(int a){
            System.out.println("base的构造方法...");
        }

        public void basesout(){
            System.out.println("base的内部方法");
        }
    }

    public static class test extends base{

        public test(){
            super(9);
            System.out.println("test 的构造方法");
        }
        public void testout(){
            System.out.println("test 的内部方法");
        }
    }

    public static void main(String[] args) {
//        int k = 0;
//        System.out.println(++k+k+++k+k+++k++);

        base base = new test();
        ((test) base).testout();
        base.basesout();

        System.out.println("----------------");
        test test = new test();
        test.testout();

    }
}
