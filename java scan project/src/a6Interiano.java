
        import java.util.*;

        public class a6Interiano {

            public static void main(String[] args) {
                

            	int N = Integer.parseInt(args[0]);
                int initialPosition = Integer.parseInt(args[1]);
                int initialDirection = Integer.parseInt(args[2]);
                List<Integer> cylinderRequests = generateCylinderRequests(N);
                // Generate fixed cylinder requests for testing
                

                // Apply SCAN algorithm
                 SCAN(cylinderRequests, initialPosition, initialDirection);


                
            }
            private static List<Integer> generateCylinderRequests(int N) {
                List<Integer> cylinderRequests = new ArrayList<>();
                Random random = new Random();

                for (int i = 0; i < N; i++) {
                    cylinderRequests.add(random.nextInt(1000));
                }

                return cylinderRequests;
            }
            static void SCAN(List<Integer> cylinderRequests, int head, int direction)
            {
                int seek_count = 0;
                int distance, cur_track;
                Vector<Integer> left = new Vector<Integer>(),
                                right = new Vector<Integer>();
                Vector<Integer> seek_sequence = new Vector<Integer>();
             
                // appending end values
                // which has to be visited
                // before reversing the direction
                if (direction == 0)
                    left.add(0);
                else if (direction == 1)
                    right.add(1000 - 1);
             
                for (int i = 0; i < cylinderRequests.size(); i++)
                {
                    if (cylinderRequests.get(i) < head)
                        left.add(cylinderRequests.get(i));
                    if (cylinderRequests.get(i) > head)
                        right.add(cylinderRequests.get(i));
                }
             
                // sorting left and right vectors
                Collections.sort(left);
                Collections.sort(right);
             
                // run the while loop two times.
                // one by one scanning right
                // and left of the head
                int run = 2;
                while (run-- >0)
                {
                    if (direction == 0)
                    {
                        for (int i = left.size() - 1; i >= 0; i--)
                        {
                            cur_track = left.get(i);
             
                            // appending current track to seek sequence
                            seek_sequence.add(cur_track);
             
                            // calculate absolute distance
                            distance = Math.abs(cur_track - head);
             
                            // increase the total count
                            seek_count += distance;
             
                            // accessed track is now the new head
                            head = cur_track;
                        }
                        direction = 1;
                    }
                    else if (direction == 1)
                    {
                        for (int i = 0; i < right.size(); i++)
                        {
                            cur_track = right.get(i);
                             
                            // appending current track to seek sequence
                            seek_sequence.add(cur_track);
             
                            // calculate absolute distance
                            distance = Math.abs(cur_track - head);
             
                            // increase the total count
                            seek_count += distance;
             
                            // accessed track is now new head
                            head = cur_track;
                        }
                        direction = 0;
                    }
                }
                System.out.println("i) " + formatCylinderAccesses(seek_sequence, head, direction));
                System.out.print("ii) Total number of seek operations = " + seek_count + "\n");
             
                
                
            }

            private static String formatCylinderAccesses(Vector<Integer> seek_sequence, int initialPosition, int initialDirection) {
                StringBuilder sb = new StringBuilder();
              
                    for (int i = 0; i < seek_sequence.size(); i++) {
                        sb.append(seek_sequence.get(i));
                        if (i != seek_sequence.size() - 1) {
                            sb.append("-");
                        }
                    }
                
                
                return sb.toString();
            }
        }
