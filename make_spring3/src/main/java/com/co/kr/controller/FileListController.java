package com.co.kr.controller;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.code.Code;
import com.co.kr.domain.BoardFileDomain;
import com.co.kr.domain.BoardListDomain;
import com.co.kr.exception.RequestException;
import com.co.kr.service.UploadService;
import com.co.kr.vo.FileListVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FileListController {
	
	@Autowired
	private UploadService uploadService;
	
	@PostMapping(value = "upload")
	public ModelAndView bdUpload(FileListVO fileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq) throws IOException, ParseException{
		ModelAndView mav = new ModelAndView();
		int bdSeq = uploadService.fileProcess(fileListVO, request, httpReq);
		//System.out.println("hello" + bdSeq); -> 이슈 수정을 위해 넣음
		fileListVO.setContent("");
		fileListVO.setTitle("");
		
		mav = bdSelectOneCall(fileListVO, String.valueOf(bdSeq),request);
		mav.setViewName("board/boardList.html");
		return mav;
	}
	
	public ModelAndView bdSelectOneCall(@ModelAttribute("fileListVO") FileListVO fileListVO, String bdSeq, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		map.put("bdSeq", Integer.parseInt(bdSeq));
		BoardListDomain boardListDomain = uploadService.boardSelectOne(map);
		System.out.println("boardListDomain"+boardListDomain);
		List<BoardFileDomain> fileList = uploadService.boardSelectOneFile(map);
		
		for(BoardFileDomain list : fileList) {
			String path = list.getUpFilePath().replace("\\\\","/");
			list.setUpFilePath(path);
		}
		mav.addObject("detail", boardListDomain);
		mav.addObject("files", fileList);
		
		session.setAttribute("files", fileList);
		
		return mav;
	}
	//detail
	@GetMapping("detail")
	public ModelAndView bdDetail(@ModelAttribute("fileListVO") FileListVO fileListVO, @RequestParam("bdSeq") String bdSeq, HttpServletRequest request) throws IOException{
		ModelAndView mav = new ModelAndView();
		mav = bdSelectOneCall(fileListVO, bdSeq, request);
		mav.setViewName("board/boardList.html");
		return mav;
	}
	//edit
	@GetMapping("edit")
	public ModelAndView edit(FileListVO fileListVO, @RequestParam("bdSeq") String bdSeq, HttpServletRequest request) throws IOException{
		ModelAndView mav = new ModelAndView();
		//System.out.println("hello " + bdSeq); -> 이슈 확인을 위해 넣음
		HashMap<String, Object> map = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		
		map.put("bdSeq",Integer.parseInt(bdSeq));
		BoardListDomain boardListDomain = uploadService.boardSelectOne(map);
		List<BoardFileDomain> fileList = uploadService.boardSelectOneFile(map);
		
		for(BoardFileDomain list : fileList) {
			String path = list.getUpFilePath().replaceAll("\\\\","/");
			list.setUpFilePath(path);
		}
		fileListVO.setSeq(boardListDomain.getBdSeq());
		fileListVO.setContent(boardListDomain.getBdContent());
		fileListVO.setTitle(boardListDomain.getBdTitle());
		fileListVO.setIsEdit("edit");
		
		mav.addObject("detail", boardListDomain);
		mav.addObject("files", fileList);
		mav.addObject("fileLen",fileList.size());
		
		mav.setViewName("board/boardEditList.html");
		return mav;
	}
	
	//수정 업데이트
	@PostMapping("editSave")
	public ModelAndView editSave(@ModelAttribute("fileListVo") FileListVO fileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq)throws IOException{
		ModelAndView mav = new ModelAndView();
		
		//저장
		uploadService.fileProcess(fileListVO, request, httpReq);
		
		mav = bdSelectOneCall(fileListVO, fileListVO.getSeq() , request);
		fileListVO.setContent("");
		fileListVO.setTitle("");
		mav.setViewName("board/boardList.html");
		return mav;
	}
	
	@GetMapping("remove")
	public ModelAndView mbRemove(@RequestParam("bdSeq") String bdSeq, HttpServletRequest request) throws IOException{
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		HashMap<String, Object>map = new HashMap<String,Object>();
		List<BoardFileDomain> fileList = null;
		if(session.getAttribute("files") != null) {
			fileList =(List<BoardFileDomain>)session.getAttribute("files");
		}
		
		map.put("bdSeq", Integer.parseInt(bdSeq));
		
		uploadService.bdContentRemove(map);
		for(BoardFileDomain list : fileList) {
			list.getUpFilePath();
			Path filePath = Paths.get(list.getUpFilePath());
			
					try {
						Files.deleteIfExists(filePath);
						
						uploadService.bdFileRemove(list);
						
					}catch (DirectoryNotEmptyException e) {
						throw RequestException.fire(Code.E404, "디렉토리가 존재하지 않습니다", HttpStatus.NOT_FOUND);
						
					}catch (IOException e) {
						e.printStackTrace();
					}
		}
		
		session.removeAttribute("files");
		mav = bdListCall();
		mav.setViewName("board/boardList.html");
		return mav;
	}
	public ModelAndView bdListCall() {
		ModelAndView mav = new ModelAndView();
		List<BoardListDomain> items = uploadService.boardList();
		mav.addObject("items", items);
		return mav;
	}
		
		
}
