package com.nobodyiam.impl;

import com.nobodyiam.api.GreetingService;
import com.nobodyiam.dto.Greeting;
import com.nobodyiam.mapper.GreetingMapper;
import com.nobodyiam.util.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Jason on 7/5/15.
 */
@Service("greetingService")
public class GreetingServiceImpl implements GreetingService {

    @Autowired
    private GreetingMapper greetingMapper;

    @Override
    public Greeting getGreeting(long greetingId) {
        return greetingMapper.getGreeting(greetingId);
    }

    @Override
    public List<Greeting> getGreetings(int limit, int offset) {
        checkArgument(limit > 0, "Limit must be positive");
        checkArgument(limit < ConstantsUtil.MaxBatchSize(), String.format("Limit must be smaller than %d", ConstantsUtil.MaxBatchSize()));
        checkArgument(offset >= 0, "Offset must be equal to or greater than 0");

        return greetingMapper.getGreetings(limit, offset);
    }

    @Override
    public long insertGreeting(Greeting greeting) {
        checkNotNull(greeting, "Greeting cannot be null");

        return greetingMapper.insertGreeting(greeting);
    }

    @Override
    public long updateGreeting(Greeting greeting) {
        checkNotNull(greeting, "Greeting cannot be null");

        return greetingMapper.updateGreeting(greeting);
    }
}