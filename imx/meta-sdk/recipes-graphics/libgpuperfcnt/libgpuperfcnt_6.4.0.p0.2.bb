DESCRIPTION = "A library to retrieve i.MX GPU performance data"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=72c0f70181bb6e83eee6aab8de12a9f3" 

SRC_URI[arm-fb.md5sum] = "614605ad9a88f441bd03d72a609918c1"
SRC_URI[arm-fb.sha256sum] = "292cf20ed89d6daa561993379afe1a467b37c89dd7c83605d06a2cf08edd1e6b"

SRC_URI[arm-wayland.md5sum] = "28f95947ac47e2e116624e56d32fb946"
SRC_URI[arm-wayland.sha256sum] = "95e6378ab9413eee2e81e5483849b9fcd675082e79cf0ce274376fa78a52e394"

SRC_URI[arm-x11.md5sum] = "9b37302b2bd8cf4d7d8256187d7bf6e2"
SRC_URI[arm-x11.sha256sum] = "5d492c57e49545b050a0b6623b5b3381b0545b188efd9553e4fd76658e87878e"

SRC_URI[aarch64-fb.md5sum] = "0b8e0943a36a76b009f9566ab9bf3e79"
SRC_URI[aarch64-fb.sha256sum] = "4952413d1c34055121bd2c202a456ae3e9a1a618a3e74d1b5c9465827e96283e"

SRC_URI[aarch64-wayland.md5sum] = "bb2f297526315eff99e0744346411b12"
SRC_URI[aarch64-wayland.sha256sum] = "b5fe0adcbc5199c86129f138b529bbf0df8da970fa94dd204f39e683def2c22a"

SRC_URI[aarch64-x11.md5sum] = "f87472887ded9a9ab2a62d8a84be728a"
SRC_URI[aarch64-x11.sha256sum] = "05633cbf3469dc14a73544cc93e4378f9a0894fc3a737805252ff8cc07820cbb"

inherit fsl-eula-unpack2 fsl-eula-graphics

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

RDEPENDS_${PN} = "imx-gpu-viv"

# Compatible only with i.MX with GPU
COMPATIBLE_MACHINE        = "(^$)"
COMPATIBLE_MACHINE_imxgpu = "${MACHINE}"
