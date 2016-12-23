oh {
    key "ar"
    desc "SpringAR"
    code """
    SpringAR.action((n) -> {
            Data data = new Data();
            data.set("id", id);
            data.insert();
        });
"""
}



ho {
    key "ru", [KeyEvent.VK_ALT, KeyEvent.VK_SHIFT, KeyEvent.VK_F10]
    key "du", [KeyEvent.VK_ALT, KeyEvent.VK_SHIFT, KeyEvent.VK_F9]
    key "tt", [KeyEvent.VK_CONTROL, KeyEvent.VK_META, KeyEvent.VK_T]
    key "cc", [KeyEvent.VK_CONTROL, KeyEvent.VK_META, KeyEvent.VK_C]
    key "xx", [KeyEvent.VK_META, KeyEvent.VK_SHIFT, KeyEvent.VK_F4]
}
