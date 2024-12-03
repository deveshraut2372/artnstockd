package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.FileManagerMasterRes;
import com.zplus.ArtnStockMongoDB.dto.res.FileManagerRes;
import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.FileManagerMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import com.zplus.ArtnStockMongoDB.service.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/file_manager_master")
public class FileManagerController {

    @Autowired
    private FileManagerService fileManagerService;

    @PostMapping("/create")
    public ResponseEntity createFileManagerMaster(@RequestBody FileManagerReqDto fileManagerReqDto)
    {
        Boolean flag = fileManagerService.createFileManagerMaster(fileManagerReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateFileManagerMaster(@RequestBody FileManagerReqDto fileManagerReqDto) {
        Boolean flag = fileManagerService.updateFileManagerMaster(fileManagerReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity getAllFileManagerMaster() {
        List list = fileManagerService.getAllFileManagerMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }

    @GetMapping(value = "/editFileManagerMaster/{fileManagerId}")
    public ResponseEntity editFileManagerMaster(@PathVariable String fileManagerId)
    {
        FileManagerMaster fileManagerMaster = fileManagerService.editFileManagerMaster(fileManagerId);
        if(fileManagerMaster!=null)
        {
            return new ResponseEntity(fileManagerMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(fileManagerMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getActiveFileManagerMaster")
    public ResponseEntity getActiveFileManagerMaster()
    {
        List list = fileManagerService.getActiveFileManagerMaster();
        return new ResponseEntity(list,HttpStatus.OK);
    }
    @GetMapping(value = "/getUserIdWiseFileManagerList/{userId}")
    public ResponseEntity getUserIdWiseFileManagerList(@PathVariable String userId)
    {
        List<FileManagerMasterRes> list = fileManagerService.getUserIdWiseFileManagerList(userId);
      return new ResponseEntity(list,HttpStatus.OK);
    }
    @GetMapping(value = "/getFileManagerIdWiseArtList/{fileManagerId}")
    public ResponseEntity getFileManagerIdWiseArtList(@PathVariable String fileManagerId)
    {
        List<ArtMaster> list = fileManagerService.getFileManagerIdWiseArtList(fileManagerId);
        return new ResponseEntity(list,HttpStatus.OK);
    }
    @GetMapping(value = "/getUserIdWiseData/{userId}")
    public ResponseEntity getUserIdWiseData(@PathVariable String userId)
    {
        List<FileManagerRes> list = fileManagerService.getUserIdWiseData(userId);
        return new ResponseEntity(list,HttpStatus.OK);
    }

    @PostMapping(value = "/fileManagerIdWiseAddArt")
    public ResponseEntity fileManagerIdWiseAddArt(@RequestBody AddArtRequestDto addArtRequestDto)
    {
        Boolean flag = fileManagerService.fileManagerIdWiseAddArt(addArtRequestDto);
        return new ResponseEntity(flag,HttpStatus.OK);
    }

    @PostMapping(value = "/fileManagerIdWiseAddArtAndAdminProduct")
    public ResponseEntity fileManagerIdWiseAddArtAndAdminProduct(@RequestBody AddArtAndAdminProductRequestDto addArtAndAdminProductRequestDto)
    {
        Boolean flag = fileManagerService.fileManagerIdWiseAddArtAndAdminProduct(addArtAndAdminProductRequestDto);
        return new ResponseEntity(flag,HttpStatus.OK);
    }

    @PostMapping(value = "/fileManagerIdWiseAddArtAndAdminProduct1")
    public ResponseEntity fileManagerIdWiseAddArtAndAdminProduct1(@RequestBody AddArtAndAdminProductRequest addArtAndAdminProductRequest)
    {
        Boolean flag = fileManagerService.fileManagerIdWiseAddArtAndAdminProduct1(addArtAndAdminProductRequest);
        return new ResponseEntity(flag,HttpStatus.OK);
    }

    @PostMapping(value = "/fileManagetIdWiseAddAdminArtProduct")
    public  ResponseEntity fileManagerIdWiseAddAdminArtProduct(@RequestBody AddAdminArtProductReq addAdminArtProductReq)
    {
        Boolean flag=fileManagerService.fileManagerIdWiseAddAdminArtProduct(addAdminArtProductReq);
        return new ResponseEntity(flag,HttpStatus.OK);
    }

    @PutMapping(value = "/updateIdWiseTitle")
    public ResponseEntity updateIdWiseTitle(@RequestBody UpdateFileManagerTitleReqDto updateFileManagerTitleReqDto)
    {
        Boolean flag = fileManagerService.updateIdWiseTitle(updateFileManagerTitleReqDto);
        return new ResponseEntity(flag,HttpStatus.OK);
    }


    @GetMapping(value = "/getFileManagerIdWiseData/{fileManagerId}/{sortType}")
    public ResponseEntity getFileManagerIdWiseData(@PathVariable String fileManagerId,@PathVariable String sortType)
    {
        List<ArtMaster> list = fileManagerService.getFileManagerIdWiseArtList1(fileManagerId,sortType);
        return new ResponseEntity(list,HttpStatus.OK);
    }


    @DeleteMapping(value = "/deleteFileManager/{fileManagerId}")
    public ResponseEntity deleteFileManager(@PathVariable String fileManagerId)
    {
        Boolean flag = fileManagerService.deleteFileManager(fileManagerId);
        if(Boolean.TRUE.equals(flag))
        {
            return new ResponseEntity(flag, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(flag,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/FileManagerFilter")
    public ResponseEntity FileManagerFilter(@RequestBody FilterReqDto filterReqDto) {
        List list = fileManagerService.FileManagerFilter(filterReqDto);
        return new ResponseEntity(list, HttpStatus.OK);
    }


    @GetMapping("/userIdWiseArtAndProducts/{userId}")
    public ResponseEntity<List> UserIdwiseArtAndProduct(String userId)
    {
        List list=new ArrayList();
        list=fileManagerService.UserIdwiseArtAndProduct(userId);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping("/getFileManagersByUserIdAndSort")
    public ResponseEntity getFileManagersByUserIdAndSort(@RequestBody FileManagerSortRequest fileManagerSortRequest)
    {
        FileManagerMasterRes fileManagerMasterRes=new FileManagerMasterRes();
        List<FileManagerMasterRes> fileManagerMasterResList=new ArrayList<>();
        fileManagerMasterResList=fileManagerService.getFileManagerByUserIdAndSort(fileManagerSortRequest);
        return new ResponseEntity(fileManagerMasterResList,HttpStatus.OK);
    }

    @PostMapping("/getFileManagersListByUserIdAndSort")
    public ResponseEntity getFileManagersListByUserIdAndSort(@RequestBody FileManagerListSortRequest fileManagerListSortRequest)
    {
        FileManagerMasterRes fileManagerMasterRes=new FileManagerMasterRes();
        List list=new ArrayList();
        list=fileManagerService.getFileManagersListByUserIdAndSort(fileManagerListSortRequest);
        System.out.println("  List size ="+list.size());
        return new ResponseEntity(list,HttpStatus.OK);
    }

    @PostMapping("/getCountByCategoryWise")
    public ResponseEntity getCountByCategoryWise(FileManagerCountReq fileManagerCountReq)
    {
            int cnt =0;
            cnt=fileManagerService.getCountByCategoryWise(fileManagerCountReq);
            return new ResponseEntity(cnt,HttpStatus.OK);
    }


//    @PostMapping("/getFileManagersCountByUserIdAndSort")
//    public ResponseEntity getFileManagersCountByUserIdAndSort(@RequestBody FileManagerSortRequest fileManagerSortRequest)
//    {
//        int count=0;
//        count=fileManagerService.getFileManagersCountByUserIdAndSort(fileManagerSortRequest);
//        return new ResponseEntity(count,HttpStatus.OK);
//    }

    @PostMapping(value = "/DeletefileManagerIdWiseAddArtAndAdminProduct")
    public ResponseEntity DeletefileManagerIdWiseAddArtAndAdminProduct(@RequestBody AddArtAndAdminProductRequestDto addArtAndAdminProductRequestDto)
    {
        Boolean flag = fileManagerService.DeletefileManagerIdWiseAddArtAndAdminProduct(addArtAndAdminProductRequestDto);
        return new ResponseEntity(flag,HttpStatus.OK);
    }

    @PostMapping(value = "/CheckFilePresentOrNot")
    public ResponseEntity CheckFilePresentOrNot(@RequestBody CheckFileReq checkFileReq)
    {
        Boolean flag=fileManagerService.CheckFilePresentOrNot(checkFileReq);
        return new ResponseEntity(flag,HttpStatus.OK);
    }





//    @GetMapping("/art/{fileManagerId}")
//    public Map<String, Object> getList(@PathVariable String fileManagerId) {
//        Map<String, Object> response = new HashMap<>();
//
//        List<ArtMaster> artMasterList = fileManagerService.getList(fileManagerId);
//
//        response.put("fileManagerId", fileManagerId);
//        response.put("artMasterList", artMasterList);
//
//        return response;
//    }



    @PutMapping(value = "/UpdateCoverImage")
    public ResponseEntity updateCoverImage(@RequestBody UpdateCoverImageReq updateCoverImageReq)
    {
        Boolean flag=fileManagerService.updateCoverImage(updateCoverImageReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
