SetCapsLockState, AlwaysOff

CapsLock & i::
if getkeystate("alt") = 0
Send, {Home}
else
Send, +{Home}
return

CapsLock & o::
if getkeystate("alt") = 0
Send, {End}
else
Send, +{End}
return


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



CapsLock::
Send, ^i
Send, {ESC}
return

CapsLock & `;::send,{LShift}


CapsLock & e:: Run https://www.infoq.cn
CapsLock & g:: Run https://www.google.com.hk


CapsLock & +::send,{Volume_Up}
CapsLock & -::send,{Volume_Down}
