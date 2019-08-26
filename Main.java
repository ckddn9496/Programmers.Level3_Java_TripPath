import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

public class Main {

	public static void main(String[] args) {
		String[][] tickets = {{"ICN", "JFK"}, {"HND", "IAD"}, {"JFK", "HND"}};
		System.out.println(Arrays.toString(new Solution().solution(tickets)));
	}
}

class Solution {
	String[][] ts;
	boolean[] used; // used ticket
	Stack<String> stack;
	String[] answer;
    public String[] solution(String[][] tickets) {
        answer = new String[tickets.length+1];
        
        // sort by airport names
        Arrays.sort(tickets, new Comparator<String[]>() {
			@Override
			public int compare(String[] o1, String[] o2) {
				if (o1[0].compareTo(o2[0]) != 0)
					return o1[0].compareTo(o2[0]);
				else return(o1[1].compareTo(o2[1]));
			}
		});
        this.ts = tickets;
        
        // find start tickets (from ICN)
        ArrayList<Integer> startIdxs = new ArrayList<>();
        for (int i = 0; i < ts.length; i++) {
        	if (ts[i][0] == "ICN") 
        		startIdxs.add(i);
        }
        
        // find case using dfs
        for (int i = 0; i < startIdxs.size(); i++) {
        	stack = new Stack<>();
        	used = new boolean[tickets.length];
        	stack.push("ICN");
        	int idx = startIdxs.get(i);
        	stack.push(ts[idx][1]);
        	used[idx] = true;	//	티켓에 대한 Start와 Dest를 먼저 stack에 푸쉬한다.(첫번째 티켓에 대한 경로 입력)
        	// 이후 dfs를 통해 경로 탐색
        	dfs(ts.length+1);
        	if (stack.size() == ts.length + 1) {
        		answer = stack.toString().substring(1, stack.toString().length()-1).trim().split(",");
        	}
        }
        
        return answer;
    }
    
    public void dfs(int n) {
    	if (stack.size() == n) {
    		return;
    	}
    		
    	for (int i = 0; i < ts.length; i++) {
    		if (ts[i][0] == stack.peek() && used[i] == false) {	//	현재위치와 출발지가 일치하며 아직 사용하지 않은 티켓이 있다면
    			stack.push(ts[i][1]);
    			dfs(n);
    		}
    	}
    	// 모든 티켓을 다 찾았다면 return
    	return;
    }
}