package ea.slartibarfast.demo.redis.web;

import ea.slartibarfast.demo.redis.model.vo.UserVo;
import ea.slartibarfast.demo.redis.service.UserService;
import ea.slartibarfast.demo.redis.web.request.AddUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserVo add(@RequestBody AddUserRequest addUserRequest) {
        return userService.save(addUserRequest);
    }

    @GetMapping("/{id}")
    public UserVo findById(@PathVariable("id") String externalId) {
        return userService.findByExternalId(externalId);
    }
}