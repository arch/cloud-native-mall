/*
 * Copyright (c) 2023 - 2024 yingtingxu(徐应庭). All rights reserved.
 */

package org.mall.edgeservice.user;

import java.util.List;

public record User(
        String username,
        String firstName,
        String lastName,
        List<String> roles) { }