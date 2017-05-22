package Training;

public class ErrorData implements Comparable<ErrorData> {
	int feature;
	double weight;
	int expected;
	
	public ErrorData(int f, double w, int e){
		feature = f;
		weight = w;
		expected = e;
	}

	@Override
	public int compareTo(ErrorData x) {
		// TODO Auto-generated method stub
		if(this.feature < x.feature) return -1;
		else if(this.feature > x.feature) return 1;
		else return 0;

	}

}
