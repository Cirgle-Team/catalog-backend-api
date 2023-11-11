package org.cirgle.catalog.presenter.dto.response

import org.cirgle.catalog.domain.model.Friend

data class FriendResponse(
    val friends: List<Friend>,

    val friendOf: List<Friend>
)