package com.elbraulio.genetical;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface Mutation<T> {
    List<T> genes(List<T> origin);
}
