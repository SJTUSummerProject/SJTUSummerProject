
class helloword{
    public enum test{
        a(1),b(2);
        // 定义私有变量
        private int nCode;

        // 构造函数，枚举类型只能为私有

        private test(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            test.values();
            return String.valueOf(this.nCode);
        }

        public int getCode(){
            return this.nCode;
        }
    }

    public static void main(String[] args){
        for(test test1 : test.values()){
            System.out.println(test1.name().toString()+"---"+test1.getCode()+"---"+test1.toString());
        }

        String theone = "a";
        for(test test1 : test.values()){
            if(theone.equals(test1.name().toString())){
                System.out.println(test1.nCode+"---"+test1.name());
            }
        }
    }
}





