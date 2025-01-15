import java.util.*;
import java.io.*;

class Main {

    static class Manager {
        int route;
        int size;
        ArrayList<int[]> changes;

        Manager(int route, int size) {
            this.route = route;
            this.size = size;
            this.changes = new ArrayList<>();
            this.changes.add(new int[]{0, size});
        }
    }

    static Manager[] planes;
    static int[] routeToPlane;

    static int getSizeAt(int planeId, int day) {
        Manager plane = planes[planeId];
        int bestCap = 0;
        int bestDay = -1;
        for (int[] pair : plane.changes) {
            int d = pair[0];
            int c = pair[1];
            if (d <= day && d >= bestDay) {
                bestDay = d;
                bestCap = c;
            }
        }
        return bestCap;
    }

    static void c(int planeId, int t) {
        planes[planeId].route = 0;
        planes[planeId].changes.add(new int[]{t+1, 0});
    }

    static void p(int planeId, int p, int t) {
        int oldCap = getSizeAt(planeId, t-1);
        planes[planeId].changes.add(new int[]{t, p});
        planes[planeId].changes.add(new int[]{t+1, oldCap});
    }

    static void a(int planeId, int p, int t) {
        planes[planeId].route = planeId;
        planes[planeId].changes.add(new int[]{t, p});
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line1 = br.readLine().split(" ");
        int n = Integer.parseInt(line1[0]);
        int q = Integer.parseInt(line1[1]);

        String[] caps = br.readLine().split(" ");
        planes = new Manager[n+1];
        routeToPlane = new int[n+1];

        for (int i=1; i<=n; i++) {
            int cap = Integer.parseInt(caps[i-1]);
            planes[i] = new Manager(i, cap);
            routeToPlane[i] = i;
        }


        for (int j=0; j<q; j++) {
            String[] query = br.readLine().split(" ");
            String type = query[0];
            switch (type) {
                case "Q" -> {
                    int startR = Integer.parseInt(query[1]);
                    int endR = Integer.parseInt(query[2]);
                    int tDay = Integer.parseInt(query[3]);

                    long answer = 0;
                    for (int r = startR; r <= endR; r++) {
                        int planeId = routeToPlane[r];
                        if (planeId != 0) {
                            int capNow = getSizeAt(planeId, tDay);
                            if (capNow > 0) {
                                long sumForPlane = 0;
                                for (int day = 0; day < tDay; day++) {
                                    sumForPlane += getSizeAt(planeId, day);
                                }
                                answer += sumForPlane;
                            }
                        }
                    }
                    System.out.println(answer);

                }
                case "C" -> {
                    int i = Integer.parseInt(query[1]);
                    int tDay = Integer.parseInt(query[2]);
                    c(i, tDay);

                }
                case "P" -> {
                    int i = Integer.parseInt(query[1]);
                    int pVal = Integer.parseInt(query[2]);
                    int tDay = Integer.parseInt(query[3]);
                    p(i, pVal, tDay);

                }
                case "A" -> {
                    int i = Integer.parseInt(query[1]);
                    int pVal = Integer.parseInt(query[2]);
                    int tDay = Integer.parseInt(query[3]);
                    a(i, pVal, tDay);

                }
                default -> System.out.println("Error");
            }
        }
    }
}
