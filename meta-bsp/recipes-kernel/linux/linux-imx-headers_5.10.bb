# Copyright 2017-2021 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Installs i.MX-specific kernel headers"
DESCRIPTION = "Installs i.MX-specific kernel headers to userspace. \
New headers are installed in ${includedir}/imx."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRCBRANCH = "lf-5.10.y"
LOCALVERSION = "-lts-5.10.y"
KERNEL_SRC ?= "git://github.com/nxp-imx/linux-imx.git;protocol=https"
SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH}"

SRCREV = "06213bb0115f587f55ec1f45fac03544855f97a7"

S = "${WORKDIR}/git"

do_compile[noexec] = "1"

IMX_UAPI_HEADERS = " \
    dma-buf.h \
    hantrodec.h \
    hx280enc.h \
    ion.h \
    ipu.h \
    isl29023.h \
    imx_vpu.h \
    mxc_asrc.h \
    mxc_dcic.h \
    mxc_mlb.h \
    mxc_sim_interface.h \
    mxc_v4l2.h \
    mxcfb.h \
    pxp_device.h \
    pxp_dma.h \
    videodev2.h \
"

do_install() {
    # We install all headers inside of B so we can copy only the
    # whitelisted ones, and there is no risk of a new header to be
    # installed by mistake.
    oe_runmake headers_install INSTALL_HDR_PATH=${B}${exec_prefix}

    # FIXME: The ion.h is still on staging so "promote" it for now
    cp ${S}/drivers/staging/android/uapi/ion.h ${B}${includedir}/linux

    # Install whitelisted headers only
    for h in ${IMX_UAPI_HEADERS}; do
        install -D -m 0644 ${B}${includedir}/linux/$h \
	                   ${D}${includedir}/imx/linux/$h
    done
}

# Allow to build empty main package, this is required in order for -dev package
# to be propagated into the SDK
#
# Without this setting the RDEPENDS in other recipes fails to find this
# package, therefore causing the -dev package also to be skipped effectively not
# populating it into SDK
ALLOW_EMPTY_${PN} = "1"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS += "unifdef-native bison-native rsync-native"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

# Restrict this recipe to NXP BSP only, this recipe is not compatible
# with mainline BSP
COMPATIBLE_HOST = '(null)'
COMPATIBLE_HOST_use-nxp-bsp = '.*'
