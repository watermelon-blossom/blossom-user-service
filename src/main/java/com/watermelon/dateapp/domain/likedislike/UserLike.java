package com.watermelon.dateapp.domain.likedislike;

import com.watermelon.dateapp.domain.user.User;
import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"from_user_id","to_user_id"})
})
public class UserLike {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUserId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUserId;
}
