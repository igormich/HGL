package utils;



public class Pair<T1,T2> {
	public T1 o1;
	public T2 o2;
	public Pair(T1 o1, T2 o2) {
		this.o1 = o1;
		this.o2 = o2;
	}
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof Pair){
			@SuppressWarnings("rawtypes")
			Pair p=(Pair) arg0;
			if((o1.equals(p.o1))&&(o2.equals(p.o2)))
				return true;
			if((o2.equals(p.o1))&&(o1.equals(p.o2)))
				return true;				
		}
		return false;
	}
	@Override
	public String toString() {
		return "Pair[" + o1 + "," + o2 + "]";
	}
	@Override
	public int hashCode() {
		int h1=o1.hashCode();
		int h2=o2.hashCode();
		return h1*h2+h1+h2;
	}
	
}
