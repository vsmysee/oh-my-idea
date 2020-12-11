SetCapsLockState, AlwaysOff


#s::Send, ^s

#a::Send, ^a

#c::Send, ^c

#v::Send, ^v

#x::Send, ^x

CapsLock & h::
if getkeystate("alt") = 0
Send, {Left}
else
Send, +{Left}
return


CapsLock & j::
if getkeystate("alt") = 0
Send, {Down}
else
Send, +{Down}
return


CapsLock & k::
if getkeystate("alt") = 0
Send, {Up}
else
Send, +{Up}
return

CapsLock & l::
if getkeystate("alt") = 0
Send, {Right}
else
Send, +{Right}
return


CapsLock & f:: Send, ^x
CapsLock & y:: Send, ^c
CapsLock & p:: Send, ^v


CapsLock & ,:: Send, {Del}              
CapsLock & .:: Send, ^{Del}             
CapsLock & /:: Send, +{End}{Del}        


CapsLock & m:: Send, {BS}               
CapsLock & n:: Send, ^{BS}              
CapsLock & b:: Send, +{Home}{Del}       


CapsLock & i::
Send, ^+0
return


CapsLock::
Send, ^i
Send, {ESC}
SwitchIME(0x08040804)
return


CapsLock & +::send,{Volume_Up}
CapsLock & -::send,{Volume_Down}

SwitchIME(dwLayout){
    HKL:=DllCall("LoadKeyboardLayout", Str, dwLayout, UInt, 1)
    ControlGetFocus,ctl,A
    SendMessage,0x50,0,HKL,%ctl%,A
}

