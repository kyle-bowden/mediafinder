package co.uk.bittwisted.repository;

import co.uk.bittwisted.domain.BaseMediaInfo;

import java.util.List;

/**
 * Created by kvfbowden on 5/25/2017.
 */
public interface ApiRepository<T extends BaseMediaInfo> {
	List<T> search(String query);
}
