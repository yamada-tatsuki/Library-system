package practice;

public class JavaPractice {

	public static void main(String[] args) {
		TodaysDate today = new TodaysDate();
		today.getClass();
		System.out.println(today.toString());

		//「String」は文字列を保持するための型です
		String itemName = "リンゴ";
		//「int」は整数を保持するための型です
		int stock = 10;
		//System.out.printin(<出力したい文字列>)はコンソールに文字を出力するためのメソッドです
		System.out.println(itemName + "は" + stock + "個あります。");
		if(stock >= 10){
			System.out.println("まだまだ余裕があるよ");
		}else if(stock < 10 && stock >=5){
			System.out.println("在庫が減ってきたよ");
		}else{
			System.out.println("残りわずか！");
		}
	}
}
