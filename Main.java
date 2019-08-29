import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Main {

	public static void main(String[] args) {
		String[][] tickets = {{"ICN", "JFK"}, {"HND", "IAD"}, {"JFK", "HND"}};
//		String[][] tickets = {{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL","SFO"}};
		System.out.println(Arrays.toString(new Solution().solution(tickets)));
	}
}

class Solution {
	
	private String[][] sortedTickets;
	int[] bestVisitPath = null;
	
	public String[] solution(String[][] tickets) {
        String[] answer = new String[tickets.length+1];
        this.sortedTickets = tickets;
        // sort by airport names
        Arrays.sort(sortedTickets, new Comparator<String[]>() {
			@Override
			public int compare(String[] o1, String[] o2) {
				if (o1[0].compareTo(o2[0]) != 0)
					return o1[0].compareTo(o2[0]);
				else return(o1[1].compareTo(o2[1]));
			}
		});
        
        // find start tickets (from ICN)
        ArrayList<Integer> startIdxs = new ArrayList<>();
        for (int i = 0; i < sortedTickets.length; i++) {
        	if (sortedTickets[i][0] == "ICN") 
        		startIdxs.add(i);
        }

        for (int i = 0; i < startIdxs.size(); i++) {
        	int[] visit = new int[this.sortedTickets.length];
        	int startIdx = startIdxs.get(i);
        	for(int j = 0; j < visit.length; j++)
        		visit[j] = -1;
        	dfs(visit, startIdx, 0);
        	if (this.bestVisitPath != null) {
//        		System.out.println("startIdx: " + startIdx);
//        		for (String[] str: this.sortedTickets) {
//        			System.out.print(Arrays.toString(str));
//        		}
//        		System.out.println(Arrays.toString(this.bestVisitPath));
        		answer[0] = this.sortedTickets[startIdx][0];
        		answer[1] = this.sortedTickets[startIdx][1];
        		int nextIdx = this.bestVisitPath[startIdx];
        		for (int j = 2; j < answer.length; j++) {
        			answer[j] = this.sortedTickets[nextIdx][1];
        			nextIdx = this.bestVisitPath[nextIdx];
        		}
        		break;
        	}
        }
//        System.out.println(Arrays.toString(answer));
        return Arrays.copyOf(answer, answer.length);
    }
    private void dfs(int[] visit, int startIdx, int n) {
    	if (n == this.sortedTickets.length-1 && this.bestVisitPath == null) {
    		this.bestVisitPath = Arrays.copyOf(visit, visit.length);
    		return;
    	}

    	String dest = this.sortedTickets[startIdx][1];
    	for (int i = 0; i < this.sortedTickets.length; i++) {
    		if (i == startIdx) continue;	//	같은 ticket일때 pass
        	String start = this.sortedTickets[i][0];
    		if (dest.equals(start) && visit[i] == -1) {
    			// 도착지와 출발지가 일치하며 아직 사용하지 않은 티켓이면
    			visit[startIdx] = i;
    			dfs(visit, i, n+1);
    			visit[startIdx] = -1;
    		}
    	}
    }
}