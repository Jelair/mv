package com.uduck.mv.service;

import com.uduck.mv.entity.dto.VideoDTO;
import com.uduck.mv.repository.VideoSearchRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VideoSearchServiceImpl implements IVideoSearchService {
    /* 分页参数 */
    private Integer PAGE_SIZE = 12;          // 每页数量
    private Integer DEFAULT_PAGE_NUMBER = 0; // 默认当前页码
    /* 搜索模式 */
    private String SCORE_MODE_SUM = "sum"; // 权重分求和模式
    private Float  MIN_SCORE = 1.0F;      // 由于无相关性的分值默认为 1 ，设置权重分最小值为 10

    @Autowired
    private VideoSearchRepository videoSearchRepository;

    @Override
    public Integer addVideo(VideoDTO videoDTO) {
        VideoDTO video = videoSearchRepository.save(videoDTO);
        return video.getId();
    }

    @Override
    public List<VideoDTO> searchVideo(Integer pageNumber, Integer pageSize, String searchContent) {
        // 校验分页参数
        if (pageSize == null || pageSize <= 0) {
            pageSize = PAGE_SIZE;
        }
        if (pageNumber == null || pageNumber < DEFAULT_PAGE_NUMBER) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        // 构建搜索查询
        SearchQuery searchQuery = getCitySearchQuery(pageNumber,pageSize,searchContent);
        Page<VideoDTO> videoDTOS = videoSearchRepository.search(searchQuery);
        return videoDTOS.getContent();
    }

    @Override
    public Integer delVideo(Integer id) {
        videoSearchRepository.deleteById(id);
        return null;
    }

    /**
     * 根据搜索词构造搜索查询语句
     *
     * 代码流程：
     *      - 权重分查询
     *      - 短语匹配
     *      - 设置权重分最小值
     *      - 设置分页参数
     *
     * @param pageNumber 当前页码
     * @param pageSize 每页大小
     * @param searchContent 搜索内容
     * @return
     */
    private SearchQuery getCitySearchQuery(Integer pageNumber, Integer pageSize, String searchContent) {
        // 短语匹配到的搜索词，求和模式累加权重分
        // 权重分查询 https://www.elastic.co/guide/c ... .html
        //   - 短语匹配 https://www.elastic.co/guide/c ... .html
        //   - 字段对应权重分设置，可以优化成 enum
        //   - 由于无相关性的分值默认为 1 ，设置权重分最小值为 10
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(QueryBuilders.matchPhraseQuery("title", searchContent),
                ScoreFunctionBuilders.weightFactorFunction(1000));
        FunctionScoreQueryBuilder functionScoreQueryBuilder2 = QueryBuilders.functionScoreQuery(QueryBuilders.matchPhraseQuery("description", searchContent),
                ScoreFunctionBuilders.weightFactorFunction(500));
        // 分页参数
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(QueryBuilders.matchQuery("title", searchContent))

                //.withQuery(functionScoreQueryBuilder2)
                .build();
    }
}
