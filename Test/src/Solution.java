import java.util.*;


class Solution {
    public int[] solution(int[] fees, String[] records) {
        int[] answer = {};

        //key - 차량 번호, value - 입장시간.
        Map<Integer, Integer> carTime = new HashMap<>();
        //key - 차량 번호, vlaue - 사용요금
        Map<Integer, Integer> cost = new TreeMap<>();

        for(int i = 0; i < records.length; i++) {

            String[] temp = records[i].split(" ");

            int time = cal_time(temp[0]);
            int carNum = Integer.parseInt(temp[1]);
            String state = temp[2];

            if(state.equals("OUT"))
            {
                int inTime = carTime.get(carNum);
                int totalTime = time - inTime;

                if(cost.containsKey(carNum))
                {
                    int a = cost.get(carNum);
                    totalTime += a;
                }
                cost.put(carNum, totalTime);
                carTime.remove(carNum);
                continue;
            }
            carTime.put(carNum, time);
        }
        //여기까지 진행헀다면 아직 안나간 차량들이 carTime에 남아있음.

        //아직 안나간 차량.
        int outTime = cal_time("23:59");
        for(int num : carTime.keySet())
        {
            int in = carTime.get(num);
            int totalTime = outTime - in;
            if(cost.containsKey(num))
            {
                int a = cost.get(num);
                totalTime += a;
            }
            cost.put(num, totalTime);
        }

        //요금 계산.
        for(int num : cost.keySet())
        {
            int totalCost = 0;
            totalCost += fees[1];
            int totalTime = cost.get(num);
            if(totalTime > fees[0])
            {
                totalTime -= fees[0];
                int b = totalTime/fees[2];
                if(totalTime%fees[2] == 0)
                    totalCost += b*fees[3];
                else
                    totalCost += (b+1)*fees[3];
                cost.put(num, totalCost);
            }

            else
                cost.put(num, totalCost);

        }

        int i = 0;
        answer = new int[cost.size()];
        for(int num : cost.keySet())
        {
            answer[i++] = cost.get(num);
        }

        return answer;
    }

    //시간: 파라미터, 리턴(분)
    public int cal_time(String a)
    {
        String[] temp = a.split(":");
        return Integer.parseInt(temp[0])*60 + Integer.parseInt(temp[1]);
    }

}