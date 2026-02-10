/*
*
*
*
*/
public class Player {
        private String name;
        private int rank;
        private int dollars;
        private int credits;
        private Role role;
        private String location = "Trailer";

        public Player(String name) {
            this.name = name;
        }

        // Getters for Player Class
        public int getCredits() {
            return credits;
        }

        public String getName() {
            return name;
        }

        public int getRank() {
            return rank;
        }

        public int getDollars() {
            return dollars;
        }

        public Role getRole() {
            return role;
        }

        public String getLocation() {
            return location;
        }

        // Setters for Player Class
        public void setCredits(int credits) {
            this.credits = credits;
        }

        public void setDollars(int dollars) {
            this.dollars = dollars;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void move(String destination) {
        }

        public void act() {
        }

        public void rehearse() {
        }
    }