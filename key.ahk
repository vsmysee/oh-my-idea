SetCapsLockState, AlwaysOff

;===========================;I = Home
CapsLock & i::
if getkeystate("alt") = 0
Send, {Home}
else
Send, +{Home}
return

;===========================;O = End
CapsLock & o::
if getkeystate("alt") = 0
Send, {End}
else
Send, +{End}
return


;===========================;H = Left
CapsLock & h::
if getkeystate("alt") = 0
Send, {Left}
else
Send, +{Left}
return

;===========================;J = Down
CapsLock & j::
if getkeystate("alt") = 0
Send, {Down}
else
Send, +{Down}
return

;===========================;K = UP
CapsLock & k::
if getkeystate("alt") = 0
Send, {Up}
else
Send, +{Up}
return

;===========================;L = Right
CapsLock & l::
if getkeystate("alt") = 0
Send, {Right}
else
Send, +{Right}
return


;;============================Editor================================||
CapsLock & x:: Send, ^x                 ; X = Cut
CapsLock & c:: Send, ^c                 ; C = Copy
CapsLock & v:: Send, ^v                 ; V = Paste


;;=============================Deletor==============================||
CapsLock & ,:: Send, {Del}              ; , = Del char after
CapsLock & .:: Send, ^{Del}             ; . = Del word after
CapsLock & /:: Send, +{End}{Del}        ; / = Del all  after

CapsLock & m:: Send, {BS}               ; m = Del char before; 
CapsLock & n:: Send, ^{BS}              ; n = Del word before; 			
CapsLock & b:: Send, +{Home}{Del}       ; b = Del all  before; 


;;============================Special Char==========================||
CapsLock & ':: Send, =                  ; ' = =
CapsLock & `;:: Send, {Enter}           ; ; = Enter
CapsLock & 8:: Send, {!}                 ; 8 = !
CapsLock & 1::send,``
CapsLock & 2::send,{~}


;;===========================Controller=============================||
CapsLock:: Send, ^i                 ; Close Tag     W = {Ctr + i}
CapsLock & u::Send, {ESC}                   ; Vimer's love


;;=========================Application==============================||
CapsLock & e:: Run https://www.infoq.cn 	
CapsLock & g:: Run https://www.google.com.hk


::vp::vsce package
