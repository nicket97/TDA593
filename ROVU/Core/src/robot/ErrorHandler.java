package robot;

import java.util.PriorityQueue;

public interface ErrorHandler {
    PriorityQueue<Error> getErrorData();

    Error getError();
}
