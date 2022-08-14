package edu.sombra.cms.controller;

import edu.sombra.cms.domain.dto.OverviewPageDTO;
import edu.sombra.cms.domain.mapper.OverviewPageMapper;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/overview")
@RequiredArgsConstructor
public class HomePageController {

    private final OverviewPageMapper overviewPageMapper;

    @GetMapping
    public OverviewPageDTO overview() throws SomethingWentWrongException {
        return overviewPageMapper.to(SecurityUtil.getLoggedUserId());
    }
}
