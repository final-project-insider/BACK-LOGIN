package com.insider.login.common;

import org.springframework.data.domain.Page;

public class Pagenation {


    public static PagingButton getPagingButtonInfo(Page page) {

        int currentPage = page.getNumber() + 1;

        int defaultButtonCount = 10;

        int startPage = (int) (Math.ceil((double) currentPage / defaultButtonCount) - 1) * defaultButtonCount + 1;
        int endPage = startPage + defaultButtonCount - 1;

        if(page.getTotalPages() < endPage) endPage = page.getTotalPages();

        if(page.getTotalPages() == 0 && endPage == 0) endPage = startPage;

        return new PagingButton(currentPage, startPage, endPage);
    }

}
