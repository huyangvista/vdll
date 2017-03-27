package vdll.math;

/**
 * 多重循环  快速构建
 * @author Hocean
 * @date 2016年10月28日 下午3:27:01
 */
public class Loop {

	//Demo
	public static void main(String[] args) {
		Test.main(null);
	}

	private int start = 0; //循环开始
	private int count = 0; //循环长度
	private int floor = 0; //循环层数
	private int[] iloops = new int[floor]; //循环层值

	//特殊功能使用
	private boolean isStop = false;
	
	/**
	 * 循环构造 
	 * @param start 循环i 初始值
	 * @param count 循环i 终止值
	 * @param floor 循环 层数
	 */
	public Loop(int start, int count, int floor) {
		this.start = start;
		this.count = count;
		this.floor = floor;
		iloops = new int[floor];
	}

	/**
	 * 开始递归
	 */
	public void loop() {
		loopRecu(0);
	}
	
	//递归主体
	private void loopRecu(int flo) {
		if(isStop()){  return; }
		callAll(iloops, flo);
		if (flo >= floor) {
			call(iloops, flo);
			return;
		}
		callFloor(iloops, flo);
		flo++;
		for (int i = start; i < count; i++) {
			iloops[flo - 1] = i;
			loopRecu(flo);
		}
	}

	////递归回掉
	private void call(int[] iloops, int floor) {
		if (iloopCall != null)	iloopCall.invo(iloops, floor);
	}

	private void callFloor(int[] iloops, int floor) {
		if (iloopCallFloor != null)	iloopCallFloor.invo(iloops, floor);
	}

	private void callAll(int[] iloops, int floor) {
		if (iloopCallAll != null) iloopCallAll.invo(iloops, floor);
	}

	/**
	 * 循环回调接口
	 * @author Hocean
	 *
	 */
	public interface ILoop {
		/**
		 * 回调
		 * @param iloops 各层的循环值
		 * @param floor 当前层数
		 */
		void invo(int[] iloops, int floor);

	}

	private ILoop iloopCall;
	private ILoop iloopCallFloor;
	private ILoop iloopCallAll;

	public ILoop getIloopCall() {
		return iloopCall;
	}
	/**
	 * 最内部回调接口
	 * @param iloopCall
	 */
	public void setIloopCall(ILoop iloopCall) {
		this.iloopCall = iloopCall;
	}

	public ILoop getIloopCallFloor() {
		return iloopCallFloor;
	}
	/**
	 * 外部回调接口
	 * @param iloopCallFloor
	 */
	public void setIloopCallFloor(ILoop iloopCallFloor) {
		this.iloopCallFloor = iloopCallFloor;
	}

	public ILoop getIloopCallAll() {
		return iloopCallAll;
	}
	/**
	 * 全部回调接口
	 * @param iloopCallAll
	 */
	public void setIloopCallAll(ILoop iloopCallAll) {
		this.iloopCallAll = iloopCallAll;
	}

	public boolean isStop() {
		return isStop;
	}
	/**
	 * 设置暂停
	 * @param isStop
	 */
	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

}
//Demo
class Test {
	static long count = 0;
	static long vqueCount = 0;

	public static void main(String[] args) {
		
		//循环 1 - 10 嵌套 3 层
		final Loop loopback = new Loop(0, 10, 3); //相当于  for (int i = 0; i < 10; i++) {   for (int i = 0; i < 10; i++) {  for (int i = 0; i < 10; i++) {  invo();//调用   }  }   } 
		loopback.setIloopCall(new Loop.ILoop() {  //最内层循环调用
			@Override
			public void invo(int[] iloops, int floor) {				
				System.out.println(String.format("第%s层循环   第%s次调用", floor, ++count));
				if(count == 666) { loopback.setStop(true); System.out.println("强制停止。");}
			}
		});
		loopback.loop();
		System.out.println("参数 new Loop(0, 10, 3)   相当于  for (int i = 0; i < 10; i++) {   for (int i = 0; i < 10; i++) {  for (int i = 0; i < 10; i++) {  invo();//调用   }  }   } ");
		System.out.println(String.format("最内层循环循应该执行 10 ^ 3 = 1000  次，在%s次处强制终止。",count));
		System.out.println();
		
		System.out.print("九九乘法表 => ");
		Loop loop = new Loop(1, 10, 2);
		loop.setIloopCallAll(new Loop.ILoop() {  //所有层循环调用
			@Override
			public void invo(int[] iloops, int floor) {
				if (floor == 1) {
					System.out.println();
				} else if (floor == 2) {
					int l = iloops[0];
					int r = iloops[1];
					if (l >= r) {
						System.out.print(String.format("%s * %s = %s  ", l, r, l * r));
					}
				}

			}
		});
		loop.loop();
		System.out.println();System.out.println();
		
		
		System.out.println("穷举解题 =>");
		System.out.println("把1-8填入等式每个数最多用一次 使等式成立  a + b = 9  b + c = 7 e - f = 2 g - h = 1  ");
		vqueCount = 0;
		loop = new Loop(1, 9, 8);
		loop.setIloopCall(new Loop.ILoop() {  //所有层循环调用
			
			@Override
			public void invo(int[] iloops, int floor) {
				if(iloops[0] + iloops[1] == 9  && iloops[2] + iloops[3] == 7 && iloops[4] - iloops[5] == 2 && iloops[6] - iloops[7] == 1){
					if(eq(0,iloops) &&eq(1,iloops) && eq(2,iloops) && eq(3,iloops) && eq(4,iloops) && eq(5,iloops) && eq(6,iloops) && eq(7,iloops)  ){		
						Object[] vss = new Object[iloops.length];
						for (int i = 0; i < vss.length; i++) {
							vss[i] = Integer.toString(iloops[i]);
						}						
						System.out.println(String.format("%s + %s = 9  %s + %s = 7  %s - %s = 2  %s - %s = 1", vss));
						vqueCount++;
					}
				}
			}
			public boolean eq(int in, int... eq){
				for (int i = 1; i < eq.length; i++) {
					if(in == i) continue;
					if(eq[in] == eq[i]){
						return false;
					}				
				}
				return true;
			}
		});
		loop.loop();		
		System.out.println("此题有"+ vqueCount +"个解!");		
		System.out.println();System.out.println();

		
		System.out.println("穷举解题 =>");
		System.out.println("把1-9填入等式每个数最多用一次 使等式成立  a + b = 9  b + c = 7 e - f = 2 g - h = 1  ");
		vqueCount = 0;
		Loop lback = loop;
		loop = new Loop(1, 10, 8);
		loop.setIloopCall(lback.getIloopCall());
		loop.loop();
		System.out.println("此题有"+ vqueCount +"个解!");	
		System.out.println();System.out.println();
	}
}
