package ru.improve.itcamp.bishop.service;


import org.springframework.stereotype.Service;
import ru.improve.itcamp.bishop.controller.WeylandArgument;

@Service
public class CallMethodService {

//    @WeylandWatchingYou
    public String callMethodWithWeyland(WeylandArgument weylandArgument) {
        if (weylandArgument.getIntArg() == 5) {
//            throw new ServiceException(ILLEGAL_VALUE);
        }
        return weylandArgument.getStringArg() + weylandArgument.getIntArg();
    }

    public String callMethodWithoutWeyland(WeylandArgument weylandArgument) {
        return weylandArgument.getStringArg() + weylandArgument.getIntArg();
    }
}
