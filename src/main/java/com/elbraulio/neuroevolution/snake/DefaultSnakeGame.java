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
        snake.add(new Position(x / 2, 0));
        snake.add(new Position(x / 2, 1));
        snake.add(new Position(x / 2, 2));
        this.treat = newtreat();
    }

    private Position newtreat() {
        Random r = new Random();
        int tx = snake.get(snake.size() - 1).x;
        int ty = snake.get(snake.size() - 1).y;
        while (snake.contains(new Position(tx, ty))) {
            tx = r.nextInt(x);
            ty = r.nextInt(y);
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
        if (treat.equals(snake.get(snake.size() - 1))) {
            treat = newtreat();
            if (!checkEnd())
                score+=50;
        } else {
            snake.remove(0);
            checkEnd();
        }
        score++;
    }

    @Override
    public int score() {
        return score;
    }

    @Override
    public Number straightDistance() {
        Position head = snake.get(snake.size() - 1);
        Position neck = snake.get(snake.size() - 2);
        int distance = 0;
        // down
        if (head.y == neck.y && head.x > neck.x) {
            for (int i = 0; i < snake.size() - 4; i++) {
                if (head.y == snake.get(i).y && head.x < snake.get(i).x) {
                    int newDistance = Math.abs(snake.get(i).x - head.x);
                    distance = distance > newDistance ? newDistance : distance;
                }
            }
            if (distance == 0) {
                distance = x - head.x;
            }
            // up
        } else if (head.y == neck.y && head.x < neck.x) {
            for (int i = 0; i < snake.size() - 4; i++) {
                if (head.y == snake.get(i).y && head.x > snake.get(i).x) {
                    int newDistance = Math.abs(snake.get(i).x - head.x);
                    distance = distance > newDistance ? newDistance : distance;
                }
            }
            if (distance == 0) {
                distance = head.x;
            }
            // left
        } else if (head.x == neck.x && head.y < neck.y) {
            for (int i = 0; i < snake.size() - 4; i++) {
                if (head.x == snake.get(i).x && head.y > snake.get(i).y) {
                    int newDistance = Math.abs(snake.get(i).y - head.y);
                    distance = distance > newDistance ? newDistance : distance;
                }
            }
            if (distance == 0) {
                distance = head.y;
            }
            // right
        } else if (head.x == neck.x && head.y > neck.y) {
            for (int i = 0; i < snake.size() - 4; i++) {
                if (head.x == snake.get(i).x && head.y < snake.get(i).y) {
                    int newDistance = Math.abs(snake.get(i).y - head.y);
                    distance = distance > newDistance ? newDistance : distance;
                }
            }
            if (distance == 0) {
                distance = y - head.y;
            }
        }
        return distance;
    }

    @Override
    public Number rightDistance() {
        Position head = snake.get(snake.size() - 1);
        Position neck = snake.get(snake.size() - 2);
        int distance = 0;
        // down
        if (head.y == neck.y && head.x > neck.x) {
            for (int i = 0; i < snake.size() - 4; i++) {
                if (head.x == snake.get(i).x && head.y > snake.get(i).y) {
                    int newDistance = Math.abs(snake.get(i).y - head.y);
                    distance = distance > newDistance ? newDistance : distance;
                }
            }
            if (distance == 0) {
                distance = head.y;
            }
            // up
        } else if (head.y == neck.y && head.x < neck.x) {
            for (int i = 0; i < snake.size() - 4; i++) {
                if (head.x == snake.get(i).x && head.y < snake.get(i).y) {
                    int newDistance = Math.abs(snake.get(i).y - head.y);
                    distance = distance > newDistance ? newDistance : distance;
                }
            }
            if (distance == 0) {
                distance = y - head.y;
            }
            // left
        } else if (head.x == neck.x && head.y < neck.y) {
            for (int i = 0; i < snake.size() - 4; i++) {
                if (head.y == snake.get(i).y && head.x > snake.get(i).x) {
                    int newDistance = Math.abs(snake.get(i).x - head.x);
                    distance = distance > newDistance ? newDistance : distance;
                }
            }
            if (distance == 0) {
                distance = head.x;
            }
            // right
        } else if (head.x == neck.x && head.y > neck.y) {
            for (int i = 0; i < snake.size() - 4; i++) {
                if (head.y == snake.get(i).y && head.x < snake.get(i).x) {
                    int newDistance = Math.abs(snake.get(i).x - head.x);
                    distance = distance > newDistance ? newDistance : distance;
                }
            }
            if (distance == 0) {
                distance = x - head.x;
            }
        }
        return distance;
    }

    @Override
    public Number leftDistance() {
        Position head = snake.get(snake.size() - 1);
        Position neck = snake.get(snake.size() - 2);
        int distance = 0;
        // down
        if (head.y == neck.y && head.x > neck.x) {
            for (int i = 0; i < snake.size() - 4; i++) {
                if (head.x == snake.get(i).x && head.y < snake.get(i).y) {
                    int newDistance = Math.abs(snake.get(i).y - head.y);
                    distance = distance > newDistance ? newDistance : distance;
                }
            }
            if (distance == 0) {
                distance = y - head.y;
            }
            // up
        } else if (head.y == neck.y && head.x < neck.x) {
            for (int i = 0; i < snake.size() - 4; i++) {
                if (head.x == snake.get(i).x && head.y > snake.get(i).y) {
                    int newDistance = Math.abs(snake.get(i).y - head.y);
                    distance = distance > newDistance ? newDistance : distance;
                }
            }
            if (distance == 0) {
                distance = head.y;
            }
            // left
        } else if (head.x == neck.x && head.y < neck.y) {
            for (int i = 0; i < snake.size() - 4; i++) {
                if (head.y == snake.get(i).y && head.x < snake.get(i).x) {
                    int newDistance = Math.abs(snake.get(i).x - head.x);
                    distance = distance > newDistance ? newDistance : distance;
                }
            }
            if (distance == 0) {
                distance = x - head.x;
            }
            // right
        } else if (head.x == neck.x && head.y > neck.y) {
            for (int i = 0; i < snake.size() - 4; i++) {
                if (head.y == snake.get(i).y && head.x > snake.get(i).x) {
                    int newDistance = Math.abs(snake.get(i).x - head.x);
                    distance = distance > newDistance ? newDistance : distance;
                }
            }
            if (distance == 0) {
                distance = head.x;
            }
        }
        return distance;
    }

    @Override
    public Position treat() {
        return treat;
    }

    @Override
    public Number isTreatRight() {
        Position head = snake.get(snake.size() - 1);
        Position neck = snake.get(snake.size() - 2);
        int is = 0;
        // down
        if (head.y == neck.y && head.x > neck.x) {
            if(head.y > treat.y)
                is = 1;
            // up
        } else if (head.y == neck.y && head.x < neck.x) {
            if(head.y < treat.y)
                is = 1;
            // left
        } else if (head.x == neck.x && head.y < neck.y) {
            if(head.x > treat.x)
                is=1;
            // right
        } else if (head.x == neck.x && head.y > neck.y) {
            if(head.x < treat.x)
                is=1;
        }
        return is;
    }

    @Override
    public Number isTreatLeft() {
        Position head = snake.get(snake.size() - 1);
        Position neck = snake.get(snake.size() - 2);
        int is = 0;
        // down
        if (head.y == neck.y && head.x > neck.x) {
            if(head.y < treat.y)
                is = 1;
            // up
        } else if (head.y == neck.y && head.x < neck.x) {
            if(head.y > treat.y)
                is = 1;
            // left
        } else if (head.x == neck.x && head.y < neck.y) {
            if(head.x < treat.x)
                is=1;
            // right
        } else if (head.x == neck.x && head.y > neck.y) {
            if(head.x > treat.x)
                is=1;
        }
        return is;
    }

    @Override
    public Number isTreatStraight() {
        Position head = snake.get(snake.size() - 1);
        Position neck = snake.get(snake.size() - 2);
        int is = 0;
        // down
        if (head.y == neck.y && head.x > neck.x) {
            if(head.x < treat.x)
                is = 1;
            // up
        } else if (head.y == neck.y && head.x < neck.x) {
            if(head.x > treat.x)
                is = 1;
            // left
        } else if (head.x == neck.x && head.y < neck.y) {
            if(head.y > treat.y)
                is=1;
            // right
        } else if (head.x == neck.x && head.y > neck.y) {
            if(head.x < treat.x)
                is=1;
        }
        return is;
    }

    @Override
    public boolean isOver() {
        return gameOver;
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
