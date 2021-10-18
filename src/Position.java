public class Position {

    int x=-1,y=-1,f=-1,g=-1,h=-1,parentX=-1,parentY=-1;
    boolean visited = false;
    AStar.Status stat =  AStar.Status.UNKNOWN;

    Position() {

        this(0,0,-1,10,-1);
    }

    Position(int x, int y){

        this(x,y,-1,10,-1);
    }

    Position(int x, int y, int g) {

        this(x,y,-1,g,-1);
    }

    Position(int x, int y, int f, int g) {

        this(x,y,f,g,-1);
    }

    Position(int x, int y, int f, int g, int h) {

        this.x = x;this.y=y;this.f=f;this.g=g;this.h=h;
    }

}
