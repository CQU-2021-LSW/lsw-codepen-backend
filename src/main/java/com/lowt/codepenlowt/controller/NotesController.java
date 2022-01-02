package com.lowt.codepenlowt.controller;


import com.lowt.codepenlowt.bean.NoteIdBean;
import com.lowt.codepenlowt.entity.TableNotes;
import com.lowt.codepenlowt.service.TableNotesService;
import com.lowt.codepenlowt.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LOW_TASTE
 * @since 2021-12-04
 */
@CrossOrigin
@RestController
@RequestMapping("notes")
public class NotesController {
    @Autowired
    TableNotesService tableNotesService;

    @PostMapping("saveCode")
    public R saveCode(@RequestBody TableNotes tableNotes) {
        try {
//            System.out.println(tableNotes);
            tableNotesService.save(tableNotes);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
        return R.ok().put("data", tableNotes);
    }

    @PostMapping("updateCode")
    public R updateCode(@RequestBody TableNotes tableNotes) {
        try {
            tableNotesService.updateById(tableNotes);
            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
    }

    /**
     * 用户自己的笔记
     *
     * @param userId
     * @return
     */
    @GetMapping("noteList")
    public R noteList(Long userId) {
        try {
            ArrayList<TableNotes> tableNotes = tableNotesService.getByUserId(userId);
            return R.ok().put("data", tableNotes);
        } catch (Exception e) {
            return R.error();
        }
    }

    @PostMapping("deleteNote")
    public R deleteNote(@RequestBody NoteIdBean noteIdBean) {
        try {
            tableNotesService.removeByIds(Arrays.asList(noteIdBean.getNoteIds()));
            return R.ok("删除noteId" + Arrays.toString(noteIdBean.getNoteIds()));
        } catch (Exception e) {
            return R.error();
        }
    }

    @GetMapping("getNote")
    public R getNote(Long noteId) {
        try {
            return R.ok().put("data", tableNotesService.getById(noteId));
        } catch (Exception e) {
            return R.error();
        }
    }

}

