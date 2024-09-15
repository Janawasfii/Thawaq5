package com.example.thawaq.Repository;
import com.example.thawaq.Model.Expert;
import com.example.thawaq.Model.Rating;
import com.example.thawaq.Model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    Request findRequestById(Integer id);

    //Final
    List<Request> findRequestByExpert(Expert expert);
}
