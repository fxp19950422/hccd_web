package cn.sportsdata.webapp.youth.common.utils;


/**
 * WebEx WebLogic server JDK is still 1.4.2.
 * So we can not use JDK 5.0 class MyStringBuilder
 * 
 * We created our own class StringBuidler here.
 * Its performance is better than StringBuffer
 * 
 * (StringBuffer 3000ms, MyStringBuilder 2300ms)
 * */
public final class MyStringBuilder
    implements java.io.Serializable
{
		private static final long serialVersionUID = -6611525011570544926L;
		
		private char data[];
    private int count;

    public MyStringBuilder() {
    	this(64);
    }

    public MyStringBuilder(int length) {
			data = new char[length];
			count = 0;
    }

    public MyStringBuilder(String str) {
			this(str.length() + 32);
			append(str);
    }

    public  int length() {
    	return count;
    }

    public void setLength(int length) {
    	this.count = length;
    }

    private void extendCapacity(int minimum) {
    	if (minimum > data.length)
    	{
	    	
	  		int newc = 2*(data.length + 1);
	  		char newValue[] = null;
	      if (newc < 0) {
	        newValue = new char[Integer.MAX_VALUE];
	      } else if (minimum > newc) {
			    newValue = new char[minimum];
	      } else {
	      	newValue = new char[newc];
	      }
				
				System.arraycopy(data, 0, newValue, 0, count);
				data = newValue;
    	}
    }

    public MyStringBuilder append(String s) {
			if (s == null) {
			    s = String.valueOf(s);
			}
		
			int newc = length() + s.length();
			extendCapacity(newc);
			s.getChars(0, s.length(), data, count);
			setLength(newc);
			return this;
    }
    
    public MyStringBuilder append(char str[], int offset, int len) {
      int newc = length() + len;
			extendCapacity(newc);
			System.arraycopy(str, offset, data, count, len);
			setLength(newc);
			return this;
    }

    public MyStringBuilder append(Object obj) {
    	return append(String.valueOf(obj));
    }
    
    public MyStringBuilder append(MyStringBuilder sb) {
			if (sb == null) {
			    sb = nullSB;
			}
			return append(sb.data, 0, sb.count);
    }

    private static final MyStringBuilder nullSB =  new MyStringBuilder("null");

    public final MyStringBuilder append(char str[]) {
			return append(str,0,str.length);
    }

    public MyStringBuilder append(boolean b) {
        if (b) {
            append("true");
        } else {
            append("false");
        }
        return this;
    }

    public final MyStringBuilder append(char c) {
			extendCapacity(count + 1);
			data[count++] = c;
			return this;
    }

    public MyStringBuilder append(int i) {
    	append(Integer.toString(i));
      return this;
    }

    public MyStringBuilder append(long l) {
      append(Long.toString(l));
      return this;
    }

    public final MyStringBuilder append(float f) {
      return append(new Float(f).toString());
    }

    public final MyStringBuilder append(double d) {
      return append(Double.toString(d));
    }

    public final String toString() {
    	return new String(this.data, 0, count);
    }

    public final String toString(int startIndex) {
    	return new String(this.data, startIndex, count - startIndex);
    }

    public final char getChar(int p) {
    	return data[p];
    }

}
