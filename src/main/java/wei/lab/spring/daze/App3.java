package wei.lab.spring.daze;

/**
 * created by weixuecai on 2019/4/10
 */
public class App3 {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>(2);

        System.out.println(stack.push(1));
        System.out.println(stack.push(2));
        System.out.println(stack.push(3));

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }

    static class Stack<T> {
        private Object[] data;
        private int curIdx; //头部

        public Stack(int capacity) {
            data = new Object[capacity];
            curIdx = -1;
        }

        public boolean push(T obj) {
            if(obj == null)
                throw new NullPointerException("null push.");

            if(curIdx >= data.length-1) {
                return false;
            } else {
                data[++curIdx] = obj;
                return true;
            }
        }

        public T pop() {
            if(curIdx < 0) {
                return null;
            }

            return (T)data[curIdx--];
        }
    }
}
