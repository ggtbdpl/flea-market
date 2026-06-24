let current = 0;
let slides = document.querySelectorAll('.slide');
let lis = document.querySelectorAll('#button li');
let banner = document.querySelector('#banner');
let prevBtn = document.querySelector('.prev');
let nextBtn = document.querySelector('.next');

function update() {
    slides.forEach((s, i) => {
        s.style.display = i === current ? 'flex' : 'none';
    });
    lis.forEach((li, i) => {
        li.classList.toggle('select', i === current);
    });
}

function next() {
    current = (current + 1) % slides.length;
    update();
}

function prev() {
    current = (current - 1 + slides.length) % slides.length;
    update();
}

let timer = setInterval(next, 3000);

banner.addEventListener('mouseenter', () => clearInterval(timer));
banner.addEventListener('mouseleave', () => {
    clearInterval(timer);
    timer = setInterval(next, 3000);
});

lis.forEach((li, j) => {
    li.addEventListener('click', () => {
        current = j;
        update();
    });
});

prevBtn.addEventListener('click', (e) => {
    e.preventDefault();
    prev();
});

nextBtn.addEventListener('click', (e) => {
    e.preventDefault();
    next();
});

update();

// 饼状图初始化代码 - 添加在轮播图脚本之后
document.addEventListener('DOMContentLoaded', function() {
    // 确保ECharts已加载
    if (typeof echarts === 'undefined') {
        return;
    }

    // 获取饼状图容器
    const chartDom = document.getElementById('tradeChart');
    if (!chartDom) {
        return;
    }

    // 初始化图表
    const myChart = echarts.init(chartDom);

    // 数据
    const tradeData = [
        { value: 35, name: '电子产品' },
        { value: 28, name: '教材书籍' },
        { value: 20, name: '服装配饰' },
        { value: 12, name: '运动器材' },
        { value: 5, name: '生活用品' }
    ];

    // 配置选项
    const option = {
        tooltip: {
            trigger: 'item',
            formatter: '{b}: {c}万元 ({d}%)',
            backgroundColor: 'rgba(255, 255, 255, 0.9)',
            borderColor: '#0a8c7f',
            borderWidth: 1,
            textStyle: {
                color: '#333'
            }
        },
        legend: {
            orient: 'vertical',
            right: 10,
            top: 'center',
            data: tradeData.map(item => item.name),
            textStyle: {
                color: '#666',
                fontSize: 12
            }
        },
        series: [
            {
                name: '交易额',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                itemStyle: {
                    borderColor: '#fff',
                    borderWidth: 2,
                    // 这里是关键：设置渐变色
                    color: function(params) {
                        // 根据索引返回不同的蓝绿色渐变
                        const colorList = [
                            {
                                type: 'linear',
                                x: 0, y: 0, x2: 1, y2: 0,
                                colorStops: [
                                    {offset: 0, color: '#0a8c7f'},
                                    {offset: 1, color: '#a8e6cf'}
                                ]
                            },
                            {
                                type: 'linear',
                                x: 0, y: 0, x2: 1, y2: 0,
                                colorStops: [
                                    {offset: 0, color: '#a8e6cf'},
                                    {offset: 1, color: '#8bc6d6'}
                                ]
                            },
                            {
                                type: 'linear',
                                x: 0, y: 0, x2: 1, y2: 0,
                                colorStops: [
                                    {offset: 0, color: '#8bc6d6'},
                                    {offset: 1, color: '#2da08c'}
                                ]
                            },
                            {
                                type: 'linear',
                                x: 0, y: 0, x2: 1, y2: 0,
                                colorStops: [
                                    {offset: 0, color: '#2da08c'},
                                    {offset: 1, color: '#0a8c7f'}
                                ]
                            },
                            {
                                type: 'linear',
                                x: 0, y: 0, x2: 1, y2: 0,
                                colorStops: [
                                    {offset: 0, color: '#1abc9c'},
                                    {offset: 1, color: '#a8e6cf'}
                                ]
                            }
                        ];
                        return colorList[params.dataIndex % colorList.length];
                    }
                },
                label: {
                    show: true,
                    formatter: '{b}: {c}万元\n({d}%)',
                    fontSize: 12,
                    color: '#333'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: 16,
                        fontWeight: 'bold'
                    },
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                labelLine: {
                    show: true
                },
                data: tradeData
            }
        ]
    };

    // 应用配置
    myChart.setOption(option);

    // 响应窗口大小变化
    window.addEventListener('resize', function() {
        myChart.resize();
    });
});

// 模态框功能
document.addEventListener('DOMContentLoaded', function() {
    // 获取DOM元素
    const publishBtn = document.querySelector('header .publish-btn');
    const loginLink = document.getElementById('accountLink');
    const publishModal = document.getElementById('publishModal');
    const loginModal = document.getElementById('loginModal');
    const publishForm = document.getElementById('publishForm');
    const loginForm = document.getElementById('loginForm');
    const imageUploadArea = document.querySelector('.image-upload-area');

    // 确保元素存在
    if (!publishBtn || !loginLink) {

        return;
    }

    // 打开模态框
    function openModal(modal) {
        if (modal) {
            modal.classList.add('active');
            document.body.style.overflow = 'hidden';
        }
    }

    // 关闭模态框
    function closeModal(modal) {
        if (modal) {
            modal.classList.remove('active');
            const anyOpen = document.querySelectorAll('.modal.active, .favorites-modal.active').length > 0;
            if (!anyOpen) {
                document.body.style.overflow = 'auto';
            }
        }
    }

    // 打开发布商品模态框
    publishBtn.addEventListener('click', function(e) {
        e.preventDefault();
        e.stopPropagation();
        openModal(publishModal);
    });

    // 打开登录模态框
    loginLink.addEventListener('click', function(e) {
        e.preventDefault();
        e.stopPropagation();
        openModal(loginModal);
    });

    // 为所有关闭按钮添加事件
    document.querySelectorAll('.modal-close').forEach(button => {
        button.addEventListener('click', function(e) {
            e.stopPropagation();
            const modal = this.closest('.modal');
            closeModal(modal);
        });
    });

    // 点击模态框外部关闭
    document.querySelectorAll('.modal').forEach(modal => {
        modal.addEventListener('click', function(e) {
            if (e.target === this) {
                closeModal(this);
            }
        });
    });

    // 提交发布商品表单
    if (publishForm) {
        publishForm.addEventListener('submit', function(e) {
            e.preventDefault();

            // 获取表单数据
            const productName = document.getElementById('productName').value;
            const productCategory = document.getElementById('productCategory').value;
            const productPrice = document.getElementById('productPrice').value;
            const productDescription = document.getElementById('productDescription').value;
            const contact = document.getElementById('contact').value;

            // 简单的表单验证
            if (!productName || !productCategory || !productPrice || !productDescription || !contact) {
                alert('请填写所有必填字段！');
                return;
            }

            // 模拟提交成功
            console.log(`商品发布成功模拟 - ${productName}`);

            // 重置表单
            publishForm.reset();

            // 关闭模态框
            closeModal(publishModal);

            // 模拟显示成功消息
            showSuccessMessage('商品发布成功！审核通过后将在平台上展示。');
        });
    }

    // 提交登录表单
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();

            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;

            // 简单的表单验证
            if (!username || !password) {
                alert('请输入用户名和密码！');
                return;
            }

            // 模拟登录成功
            console.log(`登录成功模拟 - 欢迎回来，${escapeHtml(username)}`);

            // 重置表单
            loginForm.reset();

            // 关闭模态框
            closeModal(loginModal);

            // 更新页面上的用户状态（模拟）
            updateUserStatus(username);
        });
    }

    // 图片上传区域点击事件（模拟）
    if (imageUploadArea) {
        imageUploadArea.addEventListener('click', function() {
            // 模拟文件选择
            const input = document.createElement('input');
            input.type = 'file';
            input.accept = 'image/*';
            input.multiple = true;

            input.onchange = function(e) {
                const files = e.target.files;
                if (files.length > 5) {
                    alert('最多只能上传5张图片');
                    return;
                }

                // 显示图片预览
                const previewContainer = document.querySelector('.image-preview-container');
                previewContainer.innerHTML = ''; // 清空之前的预览

                for (let i = 0; i < Math.min(files.length, 5); i++) {
                    const file = files[i];
                    const reader = new FileReader();

                    reader.onload = function(e) {
                        const imgPreview = document.createElement('div');
                        imgPreview.className = 'image-preview';
                        imgPreview.innerHTML = `
              <img src="${e.target.result}" alt="预览图片">
              <span class="remove-image">×</span>
            `;

                        // 添加删除按钮事件
                        const removeBtn = imgPreview.querySelector('.remove-image');
                        removeBtn.addEventListener('click', function() {
                            imgPreview.remove();
                        });

                        previewContainer.appendChild(imgPreview);
                    };

                    reader.readAsDataURL(file);
                }
            };

            input.click();
        });
    }

    // 社交登录按钮事件
    const socialButtons = document.querySelectorAll('.btn-social');
    socialButtons.forEach(button => {
        button.addEventListener('click', function() {
            const platform = this.classList.contains('btn-wechat') ? '微信' : 'QQ';
            console.log(`即将跳转到${platform}登录...`);
        });
    });

    // 忘记密码和注册链接事件
    const forgotPasswordLink = document.querySelector('.forgot-password');
    const registerLink = document.querySelector('.register');

    if (forgotPasswordLink) {
        forgotPasswordLink.addEventListener('click', function(e) {
            e.preventDefault();
            console.log('忘记密码功能 - 待开发');
        });
    }

    if (registerLink) {
        registerLink.addEventListener('click', function(e) {
            e.preventDefault();
            console.log('注册新账户 - 已有真实注册功能');
        });
    }

    // 显示成功消息的函数
    function showSuccessMessage(message) {
        const successMsg = document.createElement('div');
        successMsg.className = 'success-message';
        successMsg.textContent = message;
        successMsg.style.cssText = `
      position: fixed;
      top: 100px;
      right: 20px;
	  
      color: white;
      padding: 15px 20px;
      border-radius: 8px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.15);
      z-index: 3000;
      animation: slideInRight 0.3s ease-out;
    `;

        document.body.appendChild(successMsg);

        // 3秒后移除消息
        setTimeout(() => {
            successMsg.style.animation = 'fadeOut 0.3s ease-out';
            setTimeout(() => {
                document.body.removeChild(successMsg);
            }, 300);
        }, 3000);
    }

    // 更新用户状态（模拟登录后）
    function updateUserStatus(username) {
        const accountLink = document.getElementById('userCenterLink');
        if (accountLink) {
            accountLink.innerHTML = `
        <i class="fas fa-user-circle"></i>
        <span>${username}</span>
      `;

            // 添加悬停效果显示用户菜单
            accountLink.addEventListener('mouseenter', function() {
                showUserMenu(username);
            });

            let menuTimeout;
            accountLink.addEventListener('mouseleave', function() {
                menuTimeout = setTimeout(() => {
                    const menu = document.querySelector('.user-menu');
                    if (menu) menu.remove();
                }, 300);
            });

            accountLink.addEventListener('mouseenter', function() {
                clearTimeout(menuTimeout);
            });
        }
    }

    // 显示用户菜单
    function showUserMenu(username) {
        const accountLink = document.getElementById('userCenterLink');
        if (!accountLink) return;
        const rect = accountLink.getBoundingClientRect();

        const menu = document.createElement('div');
        menu.className = 'user-menu';
        menu.innerHTML = `
      <div class="user-info">
        <div class="user-name">${username}</div>
        <div class="user-email">${username.toLowerCase()}@campus.com</div>
      </div>
      <div class="menu-divider"></div>
      <a href="#" class="menu-item"><i class="fas fa-shopping-cart"></i> 我的商品</a>
      <a href="#" class="menu-item"><i class="fas fa-heart"></i> 我的收藏</a>
      <a href="#" class="menu-item"><i class="fas fa-cog"></i> 账户设置</a>
      <div class="menu-divider"></div>
      <a href="#" class="menu-item logout"><i class="fas fa-sign-out-alt"></i> 退出登录</a>
    `;

        menu.style.cssText = `
      position: absolute;
      top: ${rect.bottom + 10}px;
      right: 20px;
	  
      border-radius: 8px;
      box-shadow: 0 5px 20px rgba(0,0,0,0.15);
      width: 200px;
      z-index: 1001;
      animation: fadeIn 0.2s ease-out;
    `;

        document.body.appendChild(menu);

        // 为退出登录添加事件
        const logoutBtn = menu.querySelector('.logout');
        if (logoutBtn) {
            logoutBtn.addEventListener('click', function(e) {
                e.preventDefault();
                menu.remove();

                // 恢复原始登录链接
                const accountLink = document.getElementById('accountLink');
                if (accountLink) {
                    accountLink.innerHTML = `
            <i class="fas fa-user-circle"></i>
            <span>我的账户</span>
          `;
                }

                console.log('您已成功退出登录');
            });
        }
    }

    // 添加CSS动画关键帧
    const style = document.createElement('style');
    style.textContent = `
    @keyframes slideInRight {
      from {
        opacity: 0;
        transform: translateX(100%);
      }
      to {
        opacity: 1;
        transform: translateX(0);
      }
    }
    
    @keyframes fadeOut {
      from {
        opacity: 1;
      }
      to {
        opacity: 0;
      }
    }
    
    .image-preview {
      position: relative;
      width: 80px;
      height: 80px;
      border-radius: 8px;
      overflow: hidden;
    }
    
    .image-preview img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
    
    .remove-image {
      position: absolute;
      top: 5px;
      right: 5px;
      width: 20px;
      height: 20px;
      background-color: rgba(0,0,0,0.7);
      color: white;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      font-size: 14px;
    }
    
    .user-menu {
      font-family: inherit;
    }
    
    .user-info {
      padding: 15px;
    }
    
    .user-name {
      font-weight: 600;
      color: #333;
      margin-bottom: 5px;
    }
    
    .user-email {
      font-size: 0.9rem;
      color: #777;
    }
    
    .menu-divider {
      height: 1px;
      background-color: #eee;
      margin: 5px 0;
    }
    
    .menu-item {
      display: flex;
      align-items: center;
      padding: 12px 15px;
      color: #333;
      text-decoration: none;
      transition: background-color 0.2s;
    }
    
    .menu-item:hover {
      background-color: #f5f5f5;
    }
    
    .menu-item i {
      margin-right: 10px;
      width: 16px;
      color: #666;
    }
    
    .logout {
      color: #e74c3c;
    }
    
    .logout i {
      color: #e74c3c;
    }
  `;
    document.head.appendChild(style);
});



// HTML转义函数（防止XSS）
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// ==================== 我的收藏功能 ====================
document.addEventListener('DOMContentLoaded', function() {
    const STORAGE_KEY = 'campus_favorites';

    // 获取收藏数据
    function getFavorites() {
        try {
            return JSON.parse(localStorage.getItem(STORAGE_KEY)) || [];
        } catch {
            return [];
        }
    }

    // 保存收藏数据
    function saveFavorites(favs) {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(favs));
    }

    // 判断是否已收藏
    function isFavorited(id) {
        return getFavorites().some(f => f.id === id);
    }

    // 添加收藏
    function addFavorite(itemData) {
        const favs = getFavorites();
        if (!favs.some(f => f.id === itemData.id)) {
            favs.push(itemData);
            saveFavorites(favs);
            return true;
        }
        return false;
    }

    // 移除收藏
    function removeFavorite(id) {
        let favs = getFavorites();
        favs = favs.filter(f => f.id !== id);
        saveFavorites(favs);
    }

    // 更新收藏按钮状态
    function updateFavoriteButtons() {
        document.querySelectorAll('.item').forEach(item => {
            const id = item.dataset.id;
            const btn = item.querySelector('.favorite-btn');
            if (btn && id) {
                if (isFavorited(id)) {
                    btn.classList.add('active');
                    btn.title = '取消收藏';
                } else {
                    btn.classList.remove('active');
                    btn.title = '收藏商品';
                }
            }
        });
    }

    // 显示Toast提示
    function showToast(message, type) {
        const existing = document.querySelector('.favorite-toast');
        if (existing) existing.remove();

        const toast = document.createElement('div');
        toast.className = 'favorite-toast';
        const icon = type === 'add' ? '❤️' : '💔';
        toast.innerHTML = `<span style="font-size:20px;margin-right:8px;">${icon}</span>${message}`;
        toast.style.cssText = `
      position: fixed;
      top: 100px;
      left: 50%;
      transform: translateX(-50%);
      background: ${type === 'add' ? '#0a8c7f' : '#666'};
      color: white;
      padding: 12px 24px;
      border-radius: 25px;
      box-shadow: 0 4px 15px rgba(0,0,0,0.2);
      z-index: 3000;
      font-size: 14px;
      display: flex;
      align-items: center;
      animation: toastSlideDown 0.3s ease-out;
      white-space: nowrap;
    `;

        document.body.appendChild(toast);

        setTimeout(() => {
            toast.style.animation = 'toastFadeOut 0.3s ease-out forwards';
            setTimeout(() => toast.remove(), 300);
        }, 2000);
    }

    // 商品卡片收藏按钮点击事件（事件委托）
    document.querySelector('.goods-box')?.addEventListener('click', function(e) {
        const btn = e.target.closest('.favorite-btn');
        if (!btn) return;

        e.preventDefault();
        e.stopPropagation();

        const item = btn.closest('.item');
        const id = item.dataset.id;
        const name = item.dataset.name;
        const price = item.dataset.price;
        const img = item.dataset.img;
        const desc = item.dataset.desc;

        if (isFavorited(id)) {
            removeFavorite(id);
            btn.classList.remove('active');
            btn.title = '收藏商品';
            showToast(`已取消收藏「${name}」`, 'remove');
        } else {
            addFavorite({ id, name, price, img, desc });
            btn.classList.add('active');
            btn.classList.add('animating');
            btn.title = '取消收藏';
            showToast(`已收藏「${name}」`, 'add');
            setTimeout(() => btn.classList.remove('animating'), 600);
        }

        updateFavoritesModal();
    });

    // 渲染收藏列表
    function renderFavoritesList() {
        const listContainer = document.getElementById('favoritesList');
        const emptyContainer = document.getElementById('emptyFavorites');
        const countSpan = document.getElementById('favCount');
        const totalPriceSpan = document.getElementById('favTotalPrice');
        const avgPriceSpan = document.getElementById('favAvgPrice');
        const favs = getFavorites();

        // 更新统计
        if (countSpan) countSpan.textContent = favs.length;

        const total = favs.reduce((sum, f) => sum + (parseFloat(f.price) || 0), 0);
        if (totalPriceSpan) totalPriceSpan.textContent = '¥' + total;
        if (avgPriceSpan) avgPriceSpan.textContent = '¥' + (favs.length > 0 ? (total / favs.length).toFixed(0) : 0);

        if (favs.length === 0) {
            if (listContainer) listContainer.style.display = 'none';
            if (emptyContainer) emptyContainer.style.display = 'block';
            return;
        }

        if (listContainer) listContainer.style.display = 'grid';
        if (emptyContainer) emptyContainer.style.display = 'none';

        if (listContainer) {
            listContainer.innerHTML = favs.map(fav => `
        <div class="favorite-item" data-fav-id="${fav.id}">
          <img loading="lazy" src="${fav.img}" alt="${fav.name}" onerror="this.style.display='none'">
          <div class="favorite-info">
            <h4>${fav.name}</h4>
            <div class="favorite-price">¥${fav.price}</div>
            <p style="font-size:12px;color:#888;margin-top:4px;">${fav.desc}</p>
            <div class="favorite-actions">
              <button class="btn-remove" title="取消收藏">取消收藏</button>
              <button class="btn-view" title="查看详情">查看详情</button>
            </div>
          </div>
        </div>
      `).join('');

            // 绑定取消收藏事件
            listContainer.querySelectorAll('.btn-remove').forEach(btn => {
                btn.addEventListener('click', function(e) {
                    e.stopPropagation();
                    const item = this.closest('.favorite-item');
                    const id = item.dataset.favId;
                    const fav = favs.find(f => f.id === id);
                    removeFavorite(id);
                    item.style.transform = 'scale(0.8)';
                    item.style.opacity = '0';
                    setTimeout(() => {
                        renderFavoritesList();
                        updateFavoriteButtons();
                    }, 200);
                    if (fav) showToast(`已取消收藏「${fav.name}」`, 'remove');
                });
            });

            // 绑定查看详情事件
            listContainer.querySelectorAll('.btn-view').forEach(btn => {
                btn.addEventListener('click', function(e) {
                    e.stopPropagation();
                    const item = this.closest('.favorite-item');
                    const id = item.dataset.favId;
                    const fav = favs.find(f => f.id === id);
                    if (fav) {
                        console.log(`查看商品详情 - ${fav.name}`);
                    }
                });
            });
        }
    }

    // 更新收藏模态框
    function updateFavoritesModal() {
        renderFavoritesList();
    }

    // 打开收藏模态框
    const favoritesLink = document.querySelector('.favorites-link');
    const favoritesModal = document.getElementById('favoritesModal');

    if (favoritesLink && favoritesModal) {
        favoritesLink.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();
            renderFavoritesList();
            favoritesModal.classList.add('active');
            document.body.style.overflow = 'hidden';
        });
    }

    // 收藏模态框关闭
    if (favoritesModal) {
        const favClose = favoritesModal.querySelector('.favorites-close');
        if (favClose) {
            favClose.addEventListener('click', function(e) {
                e.stopPropagation();
                favoritesModal.classList.remove('active');
                document.body.style.overflow = 'auto';
            });
        }

        // 点击外部关闭
        favoritesModal.addEventListener('click', function(e) {
            if (e.target === this) {
                this.classList.remove('active');
                document.body.style.overflow = 'auto';
            }
        });
    }

    // 初始化
    updateFavoriteButtons();
});


// ==================== 夜间模式切换 ====================
document.addEventListener('DOMContentLoaded', function() {
    const themeToggle = document.getElementById('themeToggle');
    const moonIcon = themeToggle?.querySelector('.moon');
    const sunIcon = themeToggle?.querySelector('.sun');
    const STORAGE_KEY = 'campus_theme';

    // 获取保存的主题
    function getSavedTheme() {
        try {
            return localStorage.getItem(STORAGE_KEY) || 'light';
        } catch {
            return 'light';
        }
    }

    // 保存主题
    function saveTheme(theme) {
        try {
            localStorage.setItem(STORAGE_KEY, theme);
        } catch (e) {
            console.warn('无法保存主题设置', e);
        }
    }

    // 应用主题
    function applyTheme(theme) {
        document.documentElement.setAttribute('data-theme', theme);

        if (moonIcon && sunIcon) {
            if (theme === 'dark') {
                moonIcon.style.display = 'none';
                sunIcon.style.display = 'block';
                sunIcon.style.animation = 'rotateIn 0.5s ease-out';
            } else {
                moonIcon.style.display = 'block';
                sunIcon.style.display = 'none';
                moonIcon.style.animation = 'rotateIn 0.5s ease-out';
            }
        }

        // 更新ECharts图表颜色
        updateChartTheme(theme);
    }

    // 切换主题
    function toggleTheme() {
        const current = getSavedTheme();
        const next = current === 'light' ? 'dark' : 'light';
        applyTheme(next);
        saveTheme(next);
    }

    // 更新图表主题
    function updateChartTheme(theme) {
        const chartDom = document.getElementById('tradeChart');
        if (!chartDom || typeof echarts === 'undefined') return;

        const myChart = echarts.getInstanceByDom(chartDom);
        if (!myChart) return;

        const textColor = theme === 'dark' ? '#e0e0e0' : '#333';
        const subTextColor = theme === 'dark' ? '#b0b0b0' : '#666';

        myChart.setOption({
            legend: {
                textStyle: {
                    color: subTextColor
                }
            },
            series: [{
                label: {
                    color: textColor
                }
            }]
        });
    }

    // 绑定事件
    if (themeToggle) {
        themeToggle.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();
            toggleTheme();
        });
    }

    // 初始化
    const savedTheme = getSavedTheme();
    applyTheme(savedTheme);

    // 添加旋转动画CSS
    const rotateStyle = document.createElement('style');
    rotateStyle.textContent = `
    @keyframes rotateIn {
      from { transform: rotate(-180deg) scale(0.5); opacity: 0; }
      to { transform: rotate(0deg) scale(1); opacity: 1; }
    }
  `;
    document.head.appendChild(rotateStyle);
});

// ==================== 夜间模式切换核心逻辑 ====================
(function() {
    const html = document.documentElement;
    const STORAGE_KEY = 'campus_theme';

    // 读取保存的主题
    function getSavedTheme() {
        return localStorage.getItem(STORAGE_KEY);
    }

    // 应用主题
    function applyTheme(theme) {
        if (theme === 'dark') {
            html.setAttribute('data-theme', 'dark');
        } else {
            html.setAttribute('data-theme', 'light');
        }
        // 触发 ECharts 重绘（如果存在）
        if (typeof echarts !== 'undefined') {
            const chartDom = document.getElementById('tradeChart');
            if (chartDom) {
                const chart = echarts.getInstanceByDom(chartDom);
                if (chart) chart.resize();
            }
        }
    }

    // 切换主题（绑定到您的按钮）
    window.toggleTheme = function() {
        const current = html.getAttribute('data-theme');
        const next = current === 'dark' ? 'light' : 'dark';
        applyTheme(next);
        localStorage.setItem(STORAGE_KEY, next);
        return next;
    };

    // 初始化
    function init() {
        const saved = getSavedTheme();
        if (saved) {
            applyTheme(saved);
        } else if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
            applyTheme('dark');
        }
    }

    // 监听系统主题变化（用户未手动设置时）
    window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', (e) => {
        if (!getSavedTheme()) {
            applyTheme(e.matches ? 'dark' : 'light');
        }
    });

    init();
})();

