define([
    'jquery', './View', './DOMkit', './RenderNode'
],  function ($, View, DOMkit, RenderNode) {

    /**
     * 普通视图类（对应 JSON 对象）
     *
     * @author  TechQuery
     *
     * @class   HTMLView
     * @extends View
     *
     * @param   {jQueryAcceptable} $_View  - Container DOM of HTMLView
     * @param   {object}               [scope] - Data object as a scope
     *
     * @returns {HTMLView}             Return the last one if a HTMLView instance
     *                                 has been created on this element
     */

    function HTMLView($_View, scope) {

        var _This_ = View.call(this, $_View, scope);

        return  (_This_ !== this)  ?
            _This_ :
            this.setPrivate( {length: 0,  map: { }} );
    }

    View.extend(HTMLView, {
        is:             function () {

            return  (! $.expr[':'].list( arguments[0] ));
        },
        rawSelector:    $.makeSet('code', 'xmp', 'template'),
        getValue:       function (field) {

            if (field.type !== 'checkbox')
                return  $( field )[('value' in field) ? 'val' : 'html']();

            field = field.form.elements[ field.name ];

            return  $.likeArray( field )  ?
                $.map(field,  function (_This_) {

                    return  _This_.checked ? _This_.value : null;
                })  :  (
                    field.checked ? field.value : ''
                );
        }
    }, {
        indexOf:       Array.prototype.indexOf,
        signIn:        function (iNode) {

            for (var i = 0;  this[i];  i++)  if (this[i] == iNode)  return;

            this[this.length++] = iNode;

            var iName = (iNode instanceof RenderNode)  ?  iNode  :  [
                    iNode.__name__  ||  iNode.name
                ];

            for (var j = 0;  iName[j];  j++)
                this.watch( iName[j] ).__map__[iName[j]] =
                    (this.__map__[iName[j]] || 0)  +  Math.pow(2, i);
        },
        parsePlain:    function (iDOM) {

            Array.from(
                Array.prototype.concat.apply(
                    $.makeArray( iDOM.attributes ),  iDOM.childNodes
                ),
                function (node) {
                    if (
                        node.nodeValue  &&
                        (node.nodeType in RenderNode.Template_Type)
                    ) {
                        node = new RenderNode( node );

                        if ( node[0] )  this.signIn( node );
                    }
                },
                this
            );

            return this;
        },
        /**
         * HTML 模板解析
         *
         * @author   TechQuery
         *
         * @memberof HTMLView.prototype
         *
         * @returns  {HTMLView}  Current HTMLView
         */
        parse:         function () {

            return  this.scan(function (iNode) {

                var $_View = this.$_View,
                    tag = (iNode.tagName || '').toLowerCase();

                if ((iNode instanceof Element)  &&  (iNode !== $_View[0]))
                    switch ( tag ) {
                        case 'link':      {
                            if (('rel' in iNode)  &&  (iNode.rel != 'stylesheet'))
                                break;

                            iNode.onload = function () {

                                $( this ).replaceWith(
                                    DOMkit.fixStyle($_View, this)
                                );
                            };
                            return;
                        }
                        case 'style':     return  DOMkit.fixStyle($_View, iNode);
                        case 'script':    return  DOMkit.fixScript( iNode );
                    }

                if (iNode instanceof View) {

                    if (this.indexOf( iNode )  <  0)
                        this.parsePlain( iNode.$_View[0] ).signIn( iNode );

                } else if ( !(tag in HTMLView.rawSelector))
                    this.parsePlain( iNode );
            });
        },
        nodeOf:        function (data, exclude, forEach) {

            forEach = (forEach instanceof Function)  &&  forEach;

            var iMask = '0',  _This_ = this;

            for (var iName in data)
                if (this.__map__.hasOwnProperty( iName ))
                    iMask = $.bitOperate('|',  iMask,  this.__map__[ iName ]);

            return $.map(
                iMask.padStart(this.length, 0).split('').reverse(),
                function (bit, node) {

                    node = _This_[ node ];

                    if ((node !== exclude)  &&  (
                        (bit > 0)  ||  ((node || '').type > 1)
                    )) {
                        forEach  &&  forEach.call(_This_, node);

                        return node;
                    }
                }
            );
        },
        /**
         * 渲染视图
         *
         * @author   TechQuery
         *
         * @memberof HTMLView.prototype
         *
         * @param    {string|object} data    - Property Key or Data Object
         * @param    {*}             [value] - Property Value
         *
         * @returns  {HTMLView}      Current HTMLView
         */
        render:        function (data, value) {

            var _Data_ = { },  exclude;

            if (data instanceof Element) {

                exclude = data;

                data = exclude.getAttribute('name');

                value = HTMLView.getValue( exclude );
            }

            if (typeof data.valueOf() === 'string') {

                _Data_[data] = value;    data = _Data_;
            }

            _Data_ = this.__data__;

            this.nodeOf(_Data_.commit( data ),  exclude,  function (node) {

                if (node instanceof RenderNode)
                    node.render(this, _Data_);
                else if (node instanceof View) {

                    node.render(_Data_[node.__name__]);

                    _Data_[node.__name__] = node.__data__;
                } else
                    node.innerHTML = _Data_[ node.getAttribute('name') ];
            });

            return this;
        }
    }).registerEvent('template');

//  Render data from user input

    $('html').on('input change',  ':field',  $.throttle(function () {

        var iView = HTMLView.instanceOf( this );

        if (iView  &&  $( this ).validate())  iView.render( this );

    })).on('reset',  'form',  function () {

        var data = $.paramJSON('?'  +  $( this ).serialize());

        for (var key in data)  data[ key ] = '';

        HTMLView.instanceOf( this ).render( data );
    });

    return HTMLView;

});