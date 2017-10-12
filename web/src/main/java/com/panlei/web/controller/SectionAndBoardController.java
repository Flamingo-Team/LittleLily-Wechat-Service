package com.panlei.web.controller;

import com.panlei.web.dao.UserNjuMapper;
import com.panlei.web.model.Board;
import com.panlei.web.model.Dock;
import com.panlei.web.service.BoardService;
import com.panlei.web.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 361pa on 2017/6/19.
 */
@Controller
@RequestMapping("/bbs")
public class SectionAndBoardController {

    @Resource
    private UserNjuMapper userNjuMapper;

    @Autowired
    BoardService boardService;
    @Autowired
    SectionService sectionService;

    @RequestMapping("/section/hello")
    @ResponseBody
    public String hello() {
        return "hello panleo";
    }

    @RequestMapping(value = "/board/{boardName}",method = RequestMethod.POST)
    @ResponseBody
    public Map writePost(@PathVariable("boardName") String boardName, @RequestBody Map<String, String> postData) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        //UserNju userNju= userNjuMapper.selectUserByWebchatId(postData.get("webchatID"));
        System.out.println("send = " + postData.toString());
        String status = boardService.postWritePost(boardName, postData.get("title"),"0","0", postData.get("text"), postData.get("webchatID"), postData.get("pictures"), false);
        result.put("result", status);
        return result;
    }

    @RequestMapping(value = "/board/{boardName}/reply",method = RequestMethod.POST)
    @ResponseBody
    public Map writeReply(@PathVariable("boardName") String boardName,@RequestBody Map<String, String> postData) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        System.out.println("reply = " + postData.toString());
        //UserNju userNju= userNjuMapper.selectUserByWebchatId(postData.get("webchatID"));
        String status = boardService.postWriteReply(boardName, postData.get("url"), postData.get("title"), postData.get("text"), postData.get("webchatID"), postData.get("pictures"));
        result.put("result", status);
        return result;
    }

    @RequestMapping(value = "/section",method = RequestMethod.GET)
    @ResponseBody
    public Map getAllSections() throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("section", sectionService.getAllSections());
        return result;
    }

    @RequestMapping(value = "/section/{sectionValue}/board", method = RequestMethod.GET)
    @ResponseBody
    public Map getBoardsBySectionValue(@PathVariable("sectionValue") String sectionValue) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Board> list = boardService.getBoardsBySectionValue(sectionValue);
        result.put("board", list);
        return result;
    }

    @RequestMapping(value = "/section/board",method = RequestMethod.GET)
    @ResponseBody
    public Map getAllBoards() throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Board> list = boardService.getAllBoards();
        result.put("BoardList", list);
        return result;
    }

    @RequestMapping(value = "/section/board/{boardName}",method = RequestMethod.GET)
    @ResponseBody
    public Map getBoardsByBoardName(@PathVariable("boardName") String boardName) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Dock> list = boardService.getBoardsByBoardName(boardName);
        result.put("DockList", list);
        return result;
    }

    @RequestMapping(value = "/section/board/{boardName}/{numberFrom}",method = RequestMethod.GET)
    @ResponseBody
    public Map getBoardsByBoardName(@PathVariable("boardName") String boardName, @PathVariable("numberFrom") String numberFrom) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        //List<Dock> list = boardService.getBoardsByBoardName(boardName);
        List<Dock> list = boardService.getBoardsByBoardName(boardName, numberFrom);
        result.put("DockList", list);
        return result;
    }


}
