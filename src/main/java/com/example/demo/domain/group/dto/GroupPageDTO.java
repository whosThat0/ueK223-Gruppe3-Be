package com.example.demo.domain.group.dto;

import com.example.demo.domain.group.Group;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.util.List;


@Getter
@Setter
public class GroupPageDTO {
    private List<Group> items; // Matches frontend 'items'
    private int currentPage; // Matches frontend 'currentPage'
    private int pageSize;
    private long totalItems; // Matches frontend 'totalItems'
    private int totalPages;

    public GroupPageDTO(Page<Group> page) {
        this.items = page.getContent();
        this.currentPage = page.getNumber() + 1; // Convert 0-based to 1-based
        this.pageSize = page.getSize();
        this.totalItems = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}