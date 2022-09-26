package ru.hogwarts.school.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.Collection;

public interface AvatarService {

    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar findAvatar(Long studentId);

    Page<Avatar> getAllAvatars(Integer pageNo, Integer pageSize);

    ResponseEntity<Collection<Avatar>> getAll(Integer pageNumber, Integer pageSize);
}
