package com.elbraulio.neuroevolution.snake;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class DefaultSnakeGame implements SnakeGame<SnakeAction> {

    private final Logger logger = Logger.getLogger(DefaultSnakeGame.class);
    private final int x;
    private final int y;
    private final List<Position> snake;
    private int[] direction;
    private boolean gameOver;
    private int score;
    private Position treat;

    public DefaultSnakeGame(int x, int y) {

        this.score = 0;
        this.gameOver = false;
        direction = new int[]{0, 1};
        this.x = x;
        this.y = y;
        this.snake = new LinkedList<>();
        snake.add(new Position(x / 2, y/2));
        snake.add(new Position(x / 2, y/2 + 1));
        snake.add(new Position(x / 2, y/2 + 2));
        this.treat = newtreat();
    }

    double countx = 0;
    double county = 0;
    private Position newtreat() {
        /*Random r = new Random();
        int tx = snake.get(snake.size() - 1).x;
        int ty = snake.get(snake.size() - 1).y;
        while (snake.contains(new Position(tx, ty))) {
            tx = r.nextInt(x);
            ty = r.nextInt(y);
        }*/

        Position head = snake.get(snake.size() - 1);
        int tx = head.x;
        int ty = head.y;
        int pow = 1;
        while (snake.contains(new Position(tx, ty))) {
            countx+=3.16/4;
            county+=3.16/4;
            tx = x/2 + (int) Math.round(Math.cos(countx)*x/2*Math.pow(-1,
                    pow++));
            ty = y/2 + (int) Math.round(Math.sin(county)*y/2);
            if (tx >= x || tx < 0) {
                tx = x/2;
                countx = 0;
            }
            if (ty >= y || ty < 0) {
                ty = y/2;
                county = 0;
            }
        }
        return new Position(tx, ty);
    }

    @Override
    public List<Position> snake() {
        return snake;
    }

    @Override
    public void action(SnakeAction action) {
        if (gameOver) return;
        Position head = snake.get(snake.size() - 1);
        int[] newDirection = new int[direction.length];
        switch (action) {
            case TURN_LEFT:
                newDirection[0] = -direction[1];
                newDirection[1] = direction[0];
                direction = newDirection;
                snake.add(
                        new Position(
                                head.x + direction[0],
                                head.y + direction[1]
                        )
                );
                break;
            case TURN_RIGHT:
                newDirection[0] = direction[1];
                newDirection[1] = -direction[0];
                direction = newDirection;
                snake.add(
                        new Position(
                                head.x + direction[0],
                                head.y + direction[1]
                        )
                );
                break;
            case GO_STRAIGHT:
                snake.add(
                        new Position(
                                head.x + direction[0],
                                head.y + direction[1]
                        )
                );
                break;
        }
        Position tail = snake.get(0);
        if (treat.equals(snake.get(snake.size() - 1))) {
            treat = newtreat();
            if (!checkEnd())
                score += 100;
        } else {
            snake.remove(0);
            checkEnd();
        }

        if (isFollowingItsTail(tail)) {
            //score--;
        } else {
            //score++;
        }
    }

    private boolean isFollowingItsTail(Position tail) {
        Position head = snake.get(snake.size() - 1);
        return (head.x + 1 == tail.x || head.x - 1 == tail.x || head.x ==
        tail.x)
                && (head.y + 1 == tail.y || head.y - 1 == tail.y || head.y ==
                 tail.y);
    }

    @Override
    public int score() {
        return score;
    }

    @Override
    public Number upDistance() {
        Position head = snake.get(snake.size() - 1);
        int distance = 0;
        for (int i = 0; i < snake.size(); i++) {
            if (head.y == snake.get(i).y && head.x > snake.get(i).x) {
                int newDistance = Math.abs(snake.get(i).x - head.x);
                distance = distance > newDistance ? newDistance : distance;
            }
        }
        if (distance == 0) {
            distance = head.x;
        }
        //System.out.println("upDistance: " + distance);
        return distance;
    }

    @Override
    public Number rightDistance() {
        Position head = snake.get(snake.size() - 1);
        int distance = 0;
        for (int i = 0; i < snake.size(); i++) {
            if (head.y < snake.get(i).y && head.x == snake.get(i).x) {
                int newDistance = Math.abs(snake.get(i).y - head.y);
                distance = distance > newDistance ? newDistance : distance;
            }
        }
        if (distance == 0) {
            distance = y - head.y;
        }
        //System.out.println("rightDistance:" + distance);
        return distance;
    }

    @Override
    public Number leftDistance() {
        Position head = snake.get(snake.size() - 1);
        int distance = 0;
        for (int i = 0; i < snake.size(); i++) {
            if (head.y > snake.get(i).y && head.x == snake.get(i).x) {
                int newDistance = Math.abs(snake.get(i).y - head.y);
                distance = distance > newDistance ? newDistance : distance;
            }
        }
        if (distance == 0) {
            distance = head.y;
        }
        //System.out.println("leftDistance: " + distance);
        return distance;
    }

    @Override
    public Number downDistance(){
        Position head = snake.get(snake.size() - 1);
        int distance = 0;
        for (int i = 0; i < snake.size(); i++) {
            if (head.y == snake.get(i).y && head.x < snake.get(i).x) {
                int newDistance = Math.abs(snake.get(i).x - head.x);
                distance = distance > newDistance ? newDistance : distance;
            }
        }
        if (distance == 0) {
            distance = x - head.x;
        }
        //System.out.println("downDistance: " + distance);
        return distance;
    }

    @Override
    public Position treat() {
        return treat;
    }

    @Override
    public Number isTreatRight() {
        Position head = snake.get(snake.size() - 1);
        int is = head.y < treat.y ? 1 : 0;
        //System.out.println("isTreatRight: " + is);
        return is;
    }

    @Override
    public Number isTreatLeft() {
        Position head = snake.get(snake.size() - 1);
        int is = head.y > treat.y ? 1 : 0;
        //System.out.println("isTreatLeft: " + is);
        return is;
    }

    @Override
    public Number isTreatUp() {
        Position head = snake.get(snake.size() - 1);
        int is = head.x > treat.x ? 1 : 0;
        //System.out.println("isTreatUp: " + is);
        return is;
    }

    @Override
    public Number isTreatDown() {
        Position head = snake.get(snake.size() - 1);
        int is = head.x < treat.x ? 1 : 0;
        //System.out.println("isTreatDown: " + is);
        return is;
    }

    @Override
    public boolean isOver() {
        return gameOver;
    }

    @Override
    public Number eastDistance() {
        Position head = snake.get(snake.size() - 1);
        Number distance =
                head.x == treat.x && head.y < treat.y ? treat.y - head.y : 0;
        //System.out.println("eastDistance: " + distance);
        return distance;
    }

    @Override
    public Number westDistance() {
        Position head = snake.get(snake.size() - 1);
        Number distance = head.x == treat.x && head.y > treat.y ?
                head.y - treat.y : 0;
        //System.out.println("westDistance: " + distance);
        return distance;
    }

    @Override
    public Number northDistance() {
        Position head = snake.get(snake.size() - 1);
        Number distance = head.y == treat.y && head.x > treat.x ?
                head.x - treat.x
                : 0;
        //System.out.println("northDistance: " + distance);
        return distance;
    }

    @Override
    public Number southDistance() {
        Position head = snake.get(snake.size() - 1);
        Number distance = head.y == treat.y && head.x < treat.x ?
                treat.x - head.x : 0;
        //System.out.println("southDistance:" + distance);
        return distance;
    }

    @Override
    public Number northEastDistance() {
        Position head = snake.get(snake.size() - 1);
        Number distance =
                head.x > treat.x && head.y < treat.y ?
                head.x - treat.x + treat.y - head.y : 0;
        //System.out.println("northEastDistance: " + distance);
        return distance;
    }

    @Override
    public Number northWestDistance() {
        Position head = snake.get(snake.size() - 1);
        Number distance =
                head.x > treat.x && head.y > treat.y ?
                head.x - treat.x + head.y - treat.y : 0;
        //System.out.println("northWestDistance: " + distance);
        return distance;
    }

    @Override
    public Number southEastDistance() {
        Position head = snake.get(snake.size() - 1);
        Number distance =
                head.x < treat.x && head.y < treat.y ?
                treat.x - head.x + treat.y - head.y : 0;
        //System.out.println("southEastDistance: " + distance);
        return distance;
    }

    @Override
    public Number southWestDistance() {
        Position head = snake.get(snake.size() - 1);
        Number distance =
                head.x < treat.x && head.y > treat.y ?
                treat.x - head.x + head.y - treat.y : 0;
        //System.out.println("southWestDistance: " +distance);
        return distance;
    }

    @Override
    public Number left() {
        Position head = snake.get(snake.size() - 1);
        Position neck = snake.get(snake.size() - 2);
        // left
        if (head.x == neck.x && head.y < neck.y) {
            //System.out.println("left: 1");
            return 1;
        } else {
            //System.out.println("left: 0");
            return 0;
        }
    }

    @Override
    public Number right() {
        Position head = snake.get(snake.size() - 1);
        Position neck = snake.get(snake.size() - 2);
        if (head.x == neck.x && head.y > neck.y) {
            //System.out.println("right: 1");
            return 1;
        } else {
            //System.out.println("right: 0");
            return 0;
        }
    }

    @Override
    public Number down() {
        Position head = snake.get(snake.size() - 1);
        Position neck = snake.get(snake.size() - 2);
        // down
        if (head.y == neck.y && head.x > neck.x) {
            //System.out.println("down: 1");
            return 1;
        } else {
            //System.out.println("down: 0");
            return 0;
        }
    }

    @Override
    public Number up() {
        Position head = snake.get(snake.size() - 1);
        Position neck = snake.get(snake.size() - 2);
        if (head.y == neck.y && head.x < neck.x) {
            //System.out.println("up: 1");
            return 1;
        } else {
            //System.out.println("up: 0");
            return 0;
        }
    }

    private boolean checkEnd() {
        Position head = snake.get(snake.size() - 1);
        if (head.x >= x || head.y >= y || head.x < 0 || head.y < 0) {
            this.logger.info("You lose! Score: " + this.score());
            gameOver = true;
        }
        // no head
        for (int i = 0; i < snake.size() - 1; i++) {
            Position p = snake.get(i);
            if (p.y == head.y && p.x == head.x) {
                this.logger.info("You lose! Score: " + this.score());
                gameOver = true;
            }
        }

        return gameOver;
    }

    static class Position {
        private int x;
        private int y;

        Position(int x, int y) {

            this.x = x;
            this.y = y;
        }

        int x() {
            return this.x;
        }

        int y() {
            return this.y;
        }

        void x(int x) {
            this.x = x;
        }

        void y(int y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position position = (Position) o;

            if (x != position.x) return false;
            return y == position.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
