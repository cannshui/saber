/**
 * Define Used JS.
 * Depended on jquery.
 * Author: dn
 */

/**
 * Pagination Object.
 * 
 * @param config
 *             page: current page,
 *             total: total number,
 *             size: page size.
 *             url: url binded to page link.
 */
function PageBar(config) {
    this.page = config.page;
    this.total = config.total;
    this.size = config.size;
    this.last = parseInt(this.total / this.size);
    if (config.url) {
        this.url = config.url;
    } else {
        var url = window.location.href;
        var i = url.lastIndexOf('/');
        var cP = parseInt(url.substring(i + 1, url.length));
        if (cP == this.page) {
            this.url = url.substring(0, i + 1);
        } else {
            this.url = url + '/';
        }
    }
    if (this.total % this.size != 0) {
        this.last += 1;
    }

    this.render = function() {
        var s = '';
        for (var i = 1; i <= this.last; i++) {
            if (i != this.page) {
                s += '<a href="' + this.url + '' + i + '">' + i + '</a> ';
            } else {
                s += '<a class="p-current" href="' + this.url + i + '">' + i + '</a> ';
            }
        }
        $(s).appendTo($('.page-bar'));
    }

}

/**
 * Build article catalog(table of contents).
 */
function ArticleCatalog() {
    var jAC = $('.detail .d-content .article-catalog');
    if (jAC.length != 1) {
        return;
    }
    jAC.hide();
    var jT = jAC.prev('h2');
    var mk = '<a class="a-c-marker" href="javascript:void(0)" title="Show article category">[+]</a>';
    var aST = 'Show article category';
    var aHT = 'Hide article category';
    /* Add a marker to dom. */
    jT.append(mk);
    jT.click(function() {
        jAC.slideToggle();
        var jTA = jT.find('a');
        if (jTA.text().indexOf('+') != -1) {
            jTA.text('[-]');
            jTA.attr('title', aHT);
        } else {
            jTA.text('[+]');
            jTA.attr('title', aST);
        }
    });
    jAC.find('li').each(function() {
        var jThis = $(this);
        var jUl = jThis.children('ul');
        if (jUl.length != 1) {
            return;
        }
        jUl.hide();
        jUl.before(mk);
        jUl.prev('a').click(function() {
            jUl.slideToggle();
            var jThis = $(this);
            if (jThis.text().indexOf('+') != -1) {
                jThis.text('[-]');
                jThis.attr('title', aHT);
            } else {
                jThis.text('[+]');
                jThis.attr('title', aST);
            }
        });
    });
}

/**
 * Add scroll to top and bottom event.
 * 
 * @param config
 *             duration: animate duration.
 */
function ScrollPage(config) {
    /* Default: render nothing. */
    config = config || {};
    this.render = function() {}
    var jBody = $('html body').eq(0);
    var hasVScroll = jBody.height() > $(window).height();
    if (!hasVScroll) {
        return;
    }
    var duration = config.duration || 500;
    this.render = function() {
        var toTop = '<a href="javascript:void(0)" class="scroll-btn s-b-to-top">Top</a>';
        var toBottom = '<a href="javascript:void(0)" class="scroll-btn s-b-to-bottom">Bottom</a>';
        /* In case of more than one body el, only append to the outermost body el. */
        jBody.append(toTop + toBottom);
        $('.s-b-to-top').click(function() {
            $('html, body').animate({scrollTop: 0}, duration);
            return false;
        });
        $('.s-b-to-bottom').click(function() {
            $('html, body').animate({scrollTop: $(document).height()}, duration);
            return false;
        });
    }
}

/**
 * My normal js util. Treat this as a static class.
 */
var DNCS = {

    /** For cache. */
    _C: {},

    get: function(k) {
        return this._C[k];
    },

    set: function(k, o) {
        this._C[k] = o;
        return this;
    },

    add: function(k, o) {
        var obj = this._C[k];
        if (obj) {
            for (var k in o) {
                obj[k] = o[k];
            }
        } else {
            this._C[k] = o;
        }
        return this
    },

    /**
     * jQuery ajax with put.
     */
    xhrPut: function(url, data, sf, ef) {
        $.ajax({url: url, type: 'PUT', data: data}).done(sf).fail(ef);
    },

    /**
     * Split sting to chunks.
     */
    splitToChunks: function(s, l) {
        var t = Math.ceil(s.length / l);
        var rs = new Array(t);
        for (var i = 0; i < t; i++) {
            rs[i] = s.substring(0, l);
            s = s.substring(l);
        }
        return rs;
    },

    fmtNum: function (v, p) {
        var pw = Math.pow(10, p || 0);
        v = Math.round(v * pw) / pw + '';
        var l = v.indexOf('.');
        if (l == -1) {
            v += '.';
            l = p;
        } else {
            l = p - (v.length - l - 1);
        }
        for (var i = 0; i < l; i++) {
            v += '0';
        }
        return v;
    },

    fmtMsg: function(s, a) {
        for (var i = 0, l = a.length; i < l; i++) {
            s = s.replace('{' + (i + 1) + '}', a[i]);
        }
        return s;
    },

    /**
     * Format date to YYYY-MM-DD HH:MI:SS
     */
    fmtTime: function(date) {
        var y = date.getFullYear();
        var M = date.getMonth() + 1;
        M = M < 10 ? ("0" + M) : M;
        var d = date.getDate();
        d = d < 10 ? ("0" + d) : d;
        var H = date.getHours();
        H = H < 10 ? ("0" + H) : H;
        var m = date.getMinutes();
        m = m < 10 ? ("0" + m) : m;
        var s = date.getSeconds();
        s = s < 10 ? ("0" + s) : s;
        return y + "-" + M + "-" + d + " " + H + ":" + m + ":" + s;
    },

    /**
     * Format date to YYYY-MM-DD.
     */
    fmtDate: function(date) {
        var y = date.getFullYear();
        var M = date.getMonth() + 1;
        M = M < 10 ? ("0" + M) : M;
        var d = date.getDate();
        d = d < 10 ? ("0" + d) : d;
        return y + "-" + M + "-" + d;
    },

    /**
     * Apply validate rule to input box. Usage like: { el: jEl, required: true |
     * false, rules: [{validType: 'supported validation type', message: 'error
     * message'}*] }
     */
    applyRules: function(p) {
        var _Vs = this.get('_Vs');
        if (!_Vs) {
            _Vs = [];
        }
        _Vs.push(p);
        this.set('_Vs', _Vs);
        return this;
    },

    /**
     * Validate rules.
     */
    validate: function(els) {
        var _Vs = this.get('_Vs');
        if (!_Vs) {
            return true;
        }

        function parseValidType(validType) {
            var i = validType.indexOf('[');
            var vt = [ validType, "" ];
            if (i != -1) {
                vt[0] = validType.substring(0, i);
                vt[1] = validType.substring(i + 1, validType.length - 1);
            }
            return vt;
        }

        /* Move rules on a value validation to this function for easy-reading? */
        function validValue(val, rules) {

        }

        for (var i = 0, l = _Vs.length; i < l; i++) {
            var _V = _Vs[i];
            /* Can be a series of jquery elements. */
            var jEl = _V.el
            var rules = _V.rules;
            var required = _V.required;
            for (var k = 0, n = jEl.length; k < n; k++) {
                var val = jEl.eq(k).val();

                /* Validation of required. */
                if (required) {
                    /* The box should have a input. */
                    if (val.length == 0) {
                        console.log(rules && rules[0] && rules[0].message || '必填项');
                        return false;
                    }
                } else {
                    if (val.length == 0) {
                        continue;
                    }
                }

                for (var j = 0, m = rules.length; j < m; j++) {
                    var r = rules[j];
                    var vt = parseValidType(r.validType);
                    var v = this._V[vt[0]];
                    if (v.validate(val, vt[1])) {
                        continue;
                    }
                    console.log('Value[' + val + '] validation failed. ' + (r.message || v.message));
                    return false;
                }
            }
        }
        return true;
    },

    /** Normal validate rules. */
    _V: {
        /**
         * This input is required.
         */
        require: {
            validate: function(value) {
                return value.length > 0;
            },
            message: '必填项'
        },

        /**
         * Validate a string's length.
         */
        length: {
            validate: function(value, argStr) {
                var args = argStr.split(",");
                var l = value.length;
                var rs = false;
                if (args[0]) {
                    rs = l >= parseInt(args[0]);
                }
                if (rs && args[1]) {
                    rs = l <= parseInt(args[1]);
                }
                return rs;
            },
            message: '长度范围不合法'
        },

        /**
         * Validate as number.
         */
        number: {
            validate: function(value) {
                return /^-?\d+(\.\d+)?$/g.test(value);
            },
            message: '请输入数字'
        },

        /**
         * Validate as integer number.
         */
        integer: {
            validate: function(value) {
                return /^-?\d+$/g.test(value);
            },
            message: '请输入非负整数'
        },

        /**
         * Validate input as email.
         */
        email: {
            validate: function(value) {
                value = value.toLowerCase();
                return /^[0-9a-zA-Z_\-\.]+@(126|163|qq|gmail|sina)\.(com|cn)$/g.test(value);
            },
            message: '请输入正确邮箱'
        }
    }

}

/** Couple to pages function. */
/** Board and detail using. */
function replyComment() {
    var _r = DNCS.get('_r') || {};
    var jThis = $(this);
    var jPoster = jThis.closest('.c-poster');
    var jContent = jPoster.siblings('.c-content');
    var pN = jPoster.find('.p-name > .p-r-name').text();
    var n = '@' + pN + '\n';
    /* Cache this may be a little expensive. */
    var content = DNCS.splitToChunks(jContent.text(), 60).join('\n> ');
    var c = '> ' + content + '\n';
    var jCommentBox = DNCS.get('_jcb');
    jCommentBox.val(n + c);
    _r.cId = jThis.attr('rel');
    _r.pN = pN;
    _r.content = content;
    _r.jCReplies = jPoster.closest('.comment').find('.c-replies');
    DNCS.set('_r', _r);
}

function submitComment() {
    if(!DNCS.validate()) {
        return false;
    }
    var userName = DNCS.get('_jun').val();
    var userEmail = DNCS.get('_jue').val();
    if (!userName && userEmail || (userName && !userEmail)) {
        console.log('User name and email should both be empty or non-empty.');
        return false;
    }
    var _ai = DNCS.get('_ai');
    var param = _ai && {article: _ai} || {} ;
    var jCommentBox = DNCS.get('_jcb');
    var content = jCommentBox.val();

    var _r = DNCS.get('_r');
    if (_r) {
        /* replying. */
        var i = content.indexOf(_r.content);
        if (i == -1) {
            var ex = 'Illegal, you can\'t fix the comment values. Please re-do reply action.';
            content.val(content + '\n' + ex);
            throw Error(ex);
        } else {
            content = content.substring(i + _r.content.length + 1);
            param.reply = _r.cId;
        }
    }
    var l = content.length;
    var _cminl = DNCS.get('_cminl') || 10;
    var _cmaxl = DNCS.get('_cmaxl') || 100;
    if (l < _cminl || l > _cmaxl) {
        console.log(DNCS.fmtMsg('请输入内容，长度在 {1} ~ {2} 之间。', [_cminl, _cmaxl]));
        return false;
    }
    param.content = content;
    param['user.name'] = userName;
    param['user.email'] = userEmail;
    $.post(DNCS.get('_curl'), param).done(renderComment);
}

function renderComment(data) {
    var _r = DNCS.get('_r');
    if (_r) {
        var r = '<blockquote><p class="c-poster clearfix"><span class="p-name"><span class="p-r-name">{1}</span>@{2} </span><span class="p-time">{3}</span><a class="p-reply-btn" href="#new-comment" rel="{4}">回复</a></p><p class="c-content">{5}</p></blockquote>';
        r = DNCS.fmtMsg(r, [data.user && data.user.name || '匿名用户', _r.pN, DNCS.fmtTime(new Date()), data.id, data.content]);
        $(r).appendTo(_r.jCReplies);
        DNCS.set('_r');
    } else {
        var jCommentCnt = DNCS.get('_jcc');
        var c = parseInt(jCommentCnt.text()) + 1;
        var s = '<div class="comment"><p class="c-poster clearfix"><span class="p-name"><span class="p-r-name">{1}</span></span> <span class="p-time">{2}</span><a class="p-reply-btn" href="#new-comment" rel="{3}">回复</a></p><p class="c-content">{4}</p><div class="c-replies"></div></div>';
        s = DNCS.fmtMsg(s, [data.user && data.user.name || '匿名用户', DNCS.fmtTime(new Date()), data.id, data.content]);
        var _jcs = DNCS.get('_jcs');
        if (DNCS.get('_ab')) {
            var _jc = _jcs.find('.comment:first');
            /* In case of no comments. Heavy, since this case would happen only once. */
            if (_jc.length != 0) {
                _jc.before(s);
            } else {
                _jcs.append(s);
            }
        } else {
            $(s).appendTo(_jcs);
        }
        jCommentCnt.text(c);
    }
    DNCS.get('_jcb').val("");
}

function renderRating(rv) {
    rv = Math.round(rv);
    var jARating = DNCS.get('_jars');
    jARating.css('background', 'grey');
    var i = 0;
    for (; i < rv; i++) {
        var jTmp = jARating.eq(i);
        jTmp.css('background', 'green');
    }
}

function increaseRating() {
    var jARating = DNCS.get('_jars');
    var jARV = DNCS.get('_jarv');
    var jRC = DNCS.get('_jrc');
    jARating.css('background', 'grey');
    var _rv = jARating.index(this) + 1;
    DNCS.set('_rv', _rv);
    renderRating(_rv);
    var cRV = parseFloat(jARV.text());
    var rc = parseInt(jRC.text());
    jRC.text(rc + 1);
    jARV.text(DNCS.fmtNum((cRV * rc + _rv) / (rc + 1), 1));
    jARating.unbind('click');
    $.post(DNCS.get('_rurl'), {r: DNCS.get('_rv')})
        .done(function(resp) {
            if (resp.code == 200) {
                console.log('Thanks for your rating.');
            } else {
                console.log('Error occoured while rating.');
            }
        })
        .fail(function() { console.log('Error occoured while rating.'); });
}

function decreaseRating() {
    var jARV = DNCS.get('_jarv');
    var jRC = DNCS.get('_jrc');
    var rc = parseInt(jRC.text());
    var _rv = DNCS.get('_rv');
    if (!_rv) {
        return false;
    }
    var cRV = parseFloat(jARV.text());
    jRC.text(rc - 1);
    jARV.text(DNCS.get('_rvi'));
    jARV.unbind('click');
    DNCS.xhrPut(DNCS.get('_rurl'), {r: _rv},
        function(resp) {
            if (resp.code == 200) {
                console.log('Clear your rating success.');
            } else {
                console.log('Error occoured while clear your rating.');
            }
        },
        function() { console.log('Error occoured while clear rating.'); }
    );
}

$(function() {
    if ($('form-group')) {
        $('.f-g-i-box').focus(function() {
            var jThis = $(this);
            jThis.attr('placeholder', '');
            jThis.siblings('label').show();
        }).blur(function() {
            var jThis = $(this);
            var jSb = jThis.siblings('label');
            jThis.attr('placeholder', jSb.text());
            jSb.hide();
        });
    }
    $(document).keydown(function(e) {
        if (e.ctrlKey && e.altKey && e.keyCode == 70) {
            var jHead = $('.dncs-head');
            var jFoot = $('.dncs-foot');
            var jRight = $('.right');
            var jLeft = $('.left');
            jHead.hide('slow');
            jFoot.hide('slow');
            jRight.hide('slow');
            jLeft.css('width', '990px');
            $(this).unbind('keydown');
        }
    });
    new ScrollPage().render();
});
