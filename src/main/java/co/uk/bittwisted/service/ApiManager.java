package co.uk.bittwisted.service;

import co.uk.bittwisted.domain.BaseMediaInfo;

import java.util.List;

/**
 * Created by kvfbowden on 5/29/2017.
 */
public interface ApiManager<T extends BaseMediaInfo> {
	List<T> search(String query);
}
