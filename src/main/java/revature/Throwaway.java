package revature;

public class Throwaway {
    public static class Ship {
        private final double draft;
        private final int crew;

        public Ship(double draft, int crew) {
            this.draft = draft;
            this.crew = crew;
        }

        public boolean isWorthIt() {
            return draft - crew * 1.5 > 20;
        }
    }

    public static void main(String[] args) {
        Ship titanic = new Ship(15.0, 10);
        System.out.println(titanic.isWorthIt());
    }
}
