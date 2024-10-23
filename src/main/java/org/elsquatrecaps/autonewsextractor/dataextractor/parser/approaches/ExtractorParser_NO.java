package org.elsquatrecaps.autonewsextractor.dataextractor.parser.approaches;

import java.util.List;

/**
 *
 * @author josep
 * @param <M>
 */
public interface ExtractorParser_NO<M extends Object> {

    List<M> parseFromString(String bonText);

}
