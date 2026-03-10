//package ZombieSim;
//
//public class Human extends Entity {
//    //for randomly walking
//    int steps;
//    Direction direction;
//    boolean isRecruitable = true;
//    //constructs with steps and randomized N/S/E/W direction
//    public Human(){
//        steps = 0;
//        direction = randomDirection();
//    }
//
//    //human moves forward in 3 tiles before changing direction randomly
//    @Override
//    public Direction getMove(SimModel model){
//        // 1/3 chance to stay still - no movement
//        if(rand.nextInt(3)==0) return Direction.STAY;
//        steps++;
//        // change direction after 3 steps
//        if (steps >= 3) {
//            direction = randomDirection();
//            steps = 0;
//        }
//
//        return direction;
//    }
//
//    //human have no interaction action
//    @Override
//    // humans do not interact with other entities
//    public void interact(SimModel model) {}
//
//    public String toString() {return "H";}
//}
