# This recipe is for the i.MX fork of gstreamer1.0. For ease of
# maintenance, the top section is a verbatim copy of an OE-core
# recipe. The second section customizes the recipe for i.MX.

########### OE-core copy ##################
# Upstream hash: 633739bc912cf84c78f5ae0f7fbcb41663a05c7f

SUMMARY = "GStreamer 1.0 multimedia framework"
DESCRIPTION = "GStreamer is a multimedia framework for encoding and decoding video and sound. \
It supports a wide range of formats including mp3, ogg, avi, mpeg and quicktime."
HOMEPAGE = "http://gstreamer.freedesktop.org/"
BUGTRACKER = "https://bugzilla.gnome.org/enter_bug.cgi?product=Gstreamer"
SECTION = "multimedia"
LICENSE = "LGPLv2+"

DEPENDS = "glib-2.0 glib-2.0-native libxml2 bison-native flex-native"

inherit meson pkgconfig gettext upstream-version-is-even gobject-introspection

LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d"

S = "${WORKDIR}/gstreamer-${PV}"

SRC_URI = "https://gstreamer.freedesktop.org/src/gstreamer/gstreamer-${PV}.tar.xz \
           file://0001-gst-gstpluginloader.c-when-env-var-is-set-do-not-fal.patch \
           file://0002-Remove-unused-valgrind-detection.patch \
           file://0003-meson-Add-option-for-installed-tests.patch \
           file://0001-tests-seek-Don-t-use-too-strict-timeout-for-validati.patch \
           "
SRC_URI[sha256sum] = "9aeec99b38e310817012aa2d1d76573b787af47f8a725a65b833880a094dfbc5"

PACKAGECONFIG ??= "${@bb.utils.contains('PTEST_ENABLED', '1', 'tests', '', d)} \
                   check \
                   debug \
                   tools"

PACKAGECONFIG[debug] = "-Dgst_debug=true,-Dgst_debug=false"
PACKAGECONFIG[tracer-hooks] = "-Dtracer_hooks=true,-Dtracer_hooks=false"
PACKAGECONFIG[coretracers] = "-Dcoretracers=enabled,-Dcoretracers=disabled"
PACKAGECONFIG[check] = "-Dcheck=enabled,-Dcheck=disabled"
PACKAGECONFIG[tests] = "-Dtests=enabled -Dinstalled-tests=true,-Dtests=disabled -Dinstalled-tests=false"
PACKAGECONFIG[unwind] = "-Dlibunwind=enabled,-Dlibunwind=disabled,libunwind"
PACKAGECONFIG[dw] = "-Dlibdw=enabled,-Dlibdw=disabled,elfutils"
PACKAGECONFIG[bash-completion] = "-Dbash-completion=enabled,-Dbash-completion=disabled,bash-completion"
PACKAGECONFIG[tools] = "-Dtools=enabled,-Dtools=disabled"
PACKAGECONFIG[setcap] = "-Dptp-helper-permissions=capabilities,,libcap libcap-native"

# TODO: put this in a gettext.bbclass patch
def gettext_oemeson(d):
    if d.getVar('USE_NLS') == 'no':
        return '-Dnls=disabled'
    # Remove the NLS bits if USE_NLS is no or INHIBIT_DEFAULT_DEPS is set
    if d.getVar('INHIBIT_DEFAULT_DEPS') and not oe.utils.inherits(d, 'cross-canadian'):
        return '-Dnls=disabled'
    return '-Dnls=enabled'

EXTRA_OEMESON += " \
    -Ddoc=disabled \
    -Dexamples=disabled \
    -Ddbghelp=disabled \
    ${@gettext_oemeson(d)} \
"

GIR_MESON_ENABLE_FLAG = "enabled"
GIR_MESON_DISABLE_FLAG = "disabled"

PACKAGES += "${PN}-bash-completion"

# Add the core element plugins to the main package
FILES_${PN} += "${libdir}/gstreamer-1.0/*.so"
FILES_${PN}-dev += "${libdir}/gstreamer-1.0/*.a ${libdir}/gstreamer-1.0/include"
FILES_${PN}-bash-completion += "${datadir}/bash-completion/completions/ ${datadir}/bash-completion/helpers/gst*"
FILES_${PN}-dbg += "${datadir}/gdb ${datadir}/gstreamer-1.0/gdb"

CVE_PRODUCT = "gstreamer"

require recipes-multimedia/gstreamer/gstreamer1.0-ptest.inc

########### End of OE-core copy ###########

########### i.MX overrides ################

DEFAULT_PREFERENCE = "-1"

# Use i.MX fork of GST for customizations
SRC_URI_remove = "https://gstreamer.freedesktop.org/src/gstreamer/gstreamer-${PV}.tar.xz"
GST1.0_SRC ?= "gitsm://github.com/nxp-imx/gstreamer.git;protocol=https"
SRCBRANCH = "MM_04.06.03_2110_L5.10.y"
SRC_URI_prepend = "${GST1.0_SRC};branch=${SRCBRANCH} "
SRCREV = "a55998c70940bd183d25d29e1b82fd3bc9f43df3" 

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"

########### End of i.MX overrides #########
