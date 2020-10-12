import win from "./win/state"
import chat from "./chat/state"
import user from "./user/state"
export  default {
    ...win,
    ...chat,
    ...user
}