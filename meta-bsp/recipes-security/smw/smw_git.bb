# Copyright 2020-21 NXP

SUMMARY = "NXP i.MX Security Middleware Library"
DESCRIPTION = "NXP i.MX Security Middleware Library"
SECTION = "base"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=8636bd68fc00cc6a3809b7b58b45f982"

DEPENDS = "json-c optee-os optee-client python3-pycryptodomex-native"
DEPENDS_append_mx8qxp = " imx-seco-libs"

SRCBRANCH = "release/version_1.x"
SMW_LIB_SRC ?= "git://github.com/nxp-imx/imx-smw.git;protocol=https"
SRC_URI = "${SMW_LIB_SRC};branch=${SRCBRANCH}"
SRCREV = "4a2dbe0d616baad42d2767ba8b6e5fa44d8fb6b0"

S = "${WORKDIR}/git"

inherit cmake python3native

OPTEE_OS_TA_EXPORT_DIR_aarch64 = "${STAGING_INCDIR}/optee/export-user_ta_arm64"
OPTEE_OS_TA_EXPORT_DIR_arm = "${STAGING_INCDIR}/optee/export-user_ta_arm32"

EXTRA_OECMAKE = " \
    -DTA_DEV_KIT_ROOT=${OPTEE_OS_TA_EXPORT_DIR} \
    -DTEEC_ROOT=${STAGING_DIR_HOST} \
    -DJSONC_ROOT="${COMPONENTS_DIR}/${TARGET_ARCH}/json-c/usr" \
"
EXTRA_OECMAKE_append_mx8qxp = " -DSECO_ROOT=${STAGING_INCDIR}"

OECMAKE_TARGET_COMPILE += "build_tests"
OECMAKE_TARGET_INSTALL += "install_tests"

PACKAGES =+ "${PN}-tests"

FILES_${PN} += "${base_libdir}/optee_armtz/*"

FILES_${PN}-tests = "${bindir}/* ${datadir}/${BPN}/*"

RDEPENDS_${PN}-tests += "bash cmake"

COMPATIBLE_MACHINE = "(imx)"
